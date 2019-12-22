package com.quizter.service.test;

import com.quizter.dto.test.QuizResultDto;
import com.quizter.entity.User;
import com.quizter.entity.test.MultiVariantQuestion;
import com.quizter.entity.test.Question;
import com.quizter.entity.test.QuizResult;
import com.quizter.entity.test.ResultAnswer;
import com.quizter.entity.test.Test;
import com.quizter.mapper.test.ResultMapper;
import com.quizter.mapper.test.TestMapper;
import com.quizter.repository.QuizResultRepository;
import com.quizter.repository.ResultAnswerRepository;
import com.quizter.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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
public class QuizResultService {

    private UserService userService;
    private TestService testService;
    private TestMapper testMapper;
    private ResultMapper resultMapper;
    private QuizResultRepository quizResultRepository;
    private List<TestQuestionEvaluator> evaluators;
    private ResultAnswerRepository resultAnswerRepository;

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

    private List<ResultAnswer> getResultAnswers(QuizResult quizResult, List<QuizResultDto> quizResultDtos){
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

    public void finishQuiz(String quizResultId, List<QuizResultDto> quizResultDtos) {
        QuizResult quizResult = quizResultRepository.findById(quizResultId).orElseThrow();
        quizResult.setFinished(Instant.now());
        quizResult.setResultAnswers(getResultAnswers(quizResult, quizResultDtos));
        quizResult.setIsCompleted(true);
        quizResultRepository.save(quizResult);
    }

    public Long getDuration(String quizResultId) {
        QuizResult quizResult = findById(quizResultId).orElseThrow();
        return (quizResult.getFinished().getEpochSecond() - Instant.now().getEpochSecond());
    }

    public double evaluate(final Test test, Map<Long, String> answers) {
//		List<AbstractQuestion> abstractQuestions = test.getQuestions();
//		Map<Long, AbstractQuestion> questionById = abstractQuestions.stream().collect(Collectors.toMap(AbstractQuestion::getId, Function.identity()));
//		Map<Long, Double> awers = new HashMap<>();
//		answers.forEach((questionId, answer) -> {
//			AbstractQuestion abstractQuestion = questionById.get(questionId);
//			TestQuestionEvaluator evaluator = evaluators.stream().filter(eval -> eval.isApplicable(abstractQuestion)).findFirst()
//					.orElseThrow(() -> new IllegalStateException("Unsupported question type"));
//			awers.put(questionId, evaluator.evaluate(abstractQuestion, answer));
//		});
//		return awers.values().stream().mapToDouble(Double::doubleValue).sum();
        return 0;
    }

    @Component
    private static final class StandardEvaluator implements TestQuestionEvaluator {
        @Override
        public boolean isApplicable(Question question) {
            return question instanceof MultiVariantQuestion;
        }

        @Override
        public double evaluate(Question question, String answer) {
            return MultiVariantQuestion.class.cast(question).getAnswers().get(answer) ? question.getPrice() : 0.0;
        }
    }
}
