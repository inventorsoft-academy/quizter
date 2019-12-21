package com.quizter.service.test;

import com.quizter.dictionary.QuestionType;
import com.quizter.dto.test.QuizResultDto;
import com.quizter.entity.User;
import com.quizter.entity.test.MultiVariantQuestion;
import com.quizter.entity.test.QuizResult;
import com.quizter.entity.test.ResultAnswer;
import com.quizter.entity.test.Test;
import com.quizter.mapper.test.ResultMapper;
import com.quizter.mapper.test.TestMapper;
import com.quizter.repository.QuizResultRepository;
import com.quizter.repository.ResultAnswerRepository;
import com.quizter.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Transactional
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizResultService {

    UserService userService;
    TestService testService;
    TestMapper testMapper;
    ResultMapper resultMapper;
    QuizResultRepository quizResultRepository;
    ResultAnswerRepository resultAnswerRepository;

    public Optional<QuizResult> findById(String quizResultId) {
        return quizResultRepository.findById(quizResultId);
    }

    private String createQuizResultId() {
        String quizResultId = UUID.randomUUID().toString();
        if (quizResultRepository.findById(quizResultId).isPresent()) {
            createQuizResultId();
        }
        return quizResultId;
    }

    public String beginQuiz(Long testId) {
        QuizResult quizResult = new QuizResult();
        quizResult.setIsCompleted(false);
        quizResult.setId(createQuizResultId());
        User user = userService.getUserPrincipal();
        quizResult.setApplicant(user);
        quizResult.setStart(Instant.now());
        Test test = testMapper.toTest(testService.findTestById(testId));
        long minutes = test.getDuration();
        quizResult.setFinished(Instant.now().plus(Duration.ofMinutes(minutes)));
        quizResult.setTest(test);
        return quizResultRepository.save(quizResult).getId();
    }

    public void updateQuiz(String quizResultId, List<QuizResultDto> quizResultDtos) {
        QuizResult quizResult = quizResultRepository.findById(quizResultId).orElseThrow();
        quizResult.setResultAnswers(getResultAnswers(quizResult, quizResultDtos));
        quizResultRepository.save(quizResult);
    }

    private List<ResultAnswer> getResultAnswers(QuizResult quizResult, List<QuizResultDto> quizResultDtos) {
        final ResultAnswer[] resultAnswers = new ResultAnswer[1];
        return quizResultDtos.stream()
                .map(dto -> {
                    resultAnswers[0] = resultMapper.quizResultDtoToAnswer(dto);
                    resultAnswers[0].setQuizResult(quizResult);
                    if (resultAnswerRepository.findByQuizResultIdAndQuestionId(quizResult.getId(),
                            resultAnswers[0].getQuestion().getId()).isPresent()) {
                        resultAnswers[0].setId(resultAnswerRepository.findByQuizResultIdAndQuestionId(quizResult.getId(),
                                resultAnswers[0].getQuestion().getId()).orElseThrow().getId());
                    }
                    resultAnswerRepository.save(resultAnswers[0]);
                    return resultAnswers[0];
                }).collect(Collectors.toList());
    }

    public Double finishQuiz(String quizResultId, List<QuizResultDto> quizResultDtos) {
        QuizResult quizResult = quizResultRepository.findById(quizResultId).orElseThrow();
        quizResult.setFinished(Instant.now());
        quizResult.setResultAnswers(getResultAnswers(quizResult, quizResultDtos));
        quizResult.setIsCompleted(true);
        quizResultRepository.save(quizResult);
        return evaluate(quizResult);
    }

    public Long getDuration(String quizResultId) {
        QuizResult quizResult = findById(quizResultId).orElseThrow();
        return (quizResult.getFinished().getEpochSecond() - Instant.now().getEpochSecond());
    }

    private double evaluate(QuizResult quizResult) {
        final Double maxPrice = quizResult.getResultAnswers().stream()
                .filter(answer -> QuestionType.MULTIVARIANT.equals(answer.getQuestion().getQuestionType()))
                .map(answer -> answer.getQuestion().getPrice())
                .reduce(Double::sum).orElse(100.0);
        double totalPrice = 0;
        List<ResultAnswer> resultAnswers = quizResult.getResultAnswers().stream()
                .filter(answer -> QuestionType.MULTIVARIANT.equals(answer.getQuestion().getQuestionType()))
                .collect(Collectors.toList());
        for (ResultAnswer answer : resultAnswers) {
            MultiVariantQuestion question = (MultiVariantQuestion) answer.getQuestion();
            double price = answer.getQuestion().getPrice();
            List<String> rightAnswers = question.getAnswers().entrySet().stream()
                    .filter(qAnswer -> qAnswer.getValue().equals(true))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            long quant = answer.getStringAnswers().stream()
                    .filter(rightAnswers::contains)
                    .count();
            if (quant == rightAnswers.size()) {
                totalPrice += price;
            } else if(quant < rightAnswers.size() && quant == answer.getStringAnswers().size()){
                totalPrice +=price/2;
            }
        }
        return totalPrice*100/maxPrice;
    }

}
