package com.quizter.service.test;

import com.quizter.entity.test.Question;
import com.quizter.entity.test.MultiVariantQuestion;
import com.quizter.entity.test.Test;
import com.quizter.service.test.TestQuestionEvaluator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class TestResultService {

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
