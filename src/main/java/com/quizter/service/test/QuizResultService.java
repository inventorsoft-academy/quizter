package com.quizter.service.test;

import com.quizter.dictionary.QuestionType;
import com.quizter.dto.test.QuizResultDto;
import com.quizter.entity.test.Question;
import com.quizter.entity.test.MultiVariantQuestion;
import com.quizter.entity.test.QuizResult;
import com.quizter.entity.test.Test;
import com.quizter.mapper.test.TestMapper;
import com.quizter.repository.QuizResultRepository;
import com.quizter.service.UserService;
import com.quizter.service.test.TestQuestionEvaluator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class QuizResultService {

	private UserService userService;
	private TestService testService;
	private TestMapper testMapper;
	private QuizResultRepository quizResultRepository;
	private List<TestQuestionEvaluator> evaluators;

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

    public Long beginQuiz(Long id) {
		QuizResult quizResult = new QuizResult();
		quizResult.setApplicant(userService.getUserPrincipal());
		quizResult.setStart(Instant.now());
		Test test = testMapper.toTest(testService.findTestById(id));
		long minutes = test.getDuration();
		quizResult.setFinished(Instant.now().plus(Duration.ofMinutes(minutes)));
		quizResult.setTest(test);
		return quizResultRepository.save(quizResult).getId();
    }

	public void updateQuiz(Long resultId, List<QuizResultDto> quizResultDtos) {
		QuizResult quizResult = quizResultRepository.findById(resultId).orElseThrow();
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
