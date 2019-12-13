package com.quizter.service;

import com.quizter.entity.test.AbstractQuestion;

public interface TestQuestionEvaluator {

    boolean isApplicable(final AbstractQuestion question);

    double evaluate(final AbstractQuestion question, String answer);
}
