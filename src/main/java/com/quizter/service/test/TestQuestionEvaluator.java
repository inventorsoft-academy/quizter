package com.quizter.service.test;

import com.quizter.entity.test.Question;

public interface TestQuestionEvaluator {

    boolean isApplicable(final Question question);

    double evaluate(final Question question, String answer);
}
