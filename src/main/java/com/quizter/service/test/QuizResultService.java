package com.quizter.service.test;

import com.quizter.dto.test.QuizResultDto;
import com.quizter.entity.User;
import com.quizter.entity.test.*;
import com.quizter.mapper.test.ResultMapper;
import com.quizter.mapper.test.TestMapper;
import com.quizter.repository.QuizResultRepository;
import com.quizter.repository.ResultAnswerRepository;
import com.quizter.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class QuizResultService {

    private UserService userService;
    private TestService testService;
    private TestMapper testMapper;
    private ResultMapper resultMapper;
    private QuizResultRepository quizResultRepository;
    private List<TestQuestionEvaluator> evaluators;
    private ResultAnswerRepository resultAnswerRepository;

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

    public Optional<QuizResult> findById(Long id) {
        return quizResultRepository.findById(id);
    }

    public Long beginQuiz(Long id) {
        QuizResult quizResult = new QuizResult();
//		User user = userService.getUserPrincipal();
//		quizResult.setApplicant(user);
//		quizResult.setStart(Instant.now());
        Test test = testMapper.toTest(testService.findTestById(id));
//		long minutes = test.getDuration();
//		quizResult.setFinished(Instant.now().plus(Duration.ofMinutes(minutes)));
        quizResult.setTest(test);
        return quizResultRepository.save(quizResult).getId();
    }

    public void updateQuiz(Long resultId, List<QuizResultDto> quizResultDtos) {
        QuizResult quizResult = quizResultRepository.findById(resultId).orElseThrow();
        final ResultAnswer[] resultAnswer = new ResultAnswer[1];
        List<ResultAnswer> answers = quizResultDtos.stream()
                .map(dto -> {
                    resultAnswer[0] = resultMapper.quizResultDtoToAnswer(dto);
                    resultAnswer[0].setQuizResult(quizResult);
                    resultAnswerRepository.save(resultAnswer[0]);
                    return resultAnswer[0];
                })
                .collect(Collectors.toList());
        log.info("Answers = " + answers);
        quizResult.setResultAnswers(answers);
        quizResultRepository.save(quizResult);
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
