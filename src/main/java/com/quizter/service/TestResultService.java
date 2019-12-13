package com.quizter.service;

import com.quizter.entity.test.AbstractQuestion;
import com.quizter.entity.test.MultiVariantQuestion;
import com.quizter.entity.test.Test;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TestResultService<>{

    private List<TestQuestionEvaluator> evaluators;

    public double evaluate(final Test test, Map<Long, String> answers) {
        Map<Long, AbstractQuestion> questionById = new HashMap<>();

        for (T )

        Map<Long, Double> awers = new HashMap<>();
        answers.forEach((questionId, answer) -> {
            AbstractQuestion abstractQuestion = questionById.get(questionId);
            TestQuestionEvaluator evaluator = evaluators.stream()
                    .filter(eval -> eval.isApplicable(abstractQuestion))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Unsupported question type"));
            awers.put(questionId, evaluator.evaluate(abstractQuestion, answer));
        });
        return awers.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    @Component
    private static final class StandardEvaluator implements TestQuestionEvaluator {
        @Override
        public boolean isApplicable(AbstractQuestion question) {
            return question instanceof MultiVariantQuestion;
        }

        @Override
        public double evaluate(AbstractQuestion question, String answer) {
            return MultiVariantQuestion.class.cast(question).getAnswers().get(answer) ? question.getPrice() : 0.0;
        }
    }
}
