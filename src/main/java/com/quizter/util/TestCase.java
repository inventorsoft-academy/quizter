package com.quizter.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Delegate;

import java.util.List;

public interface TestCase {

    Output process(Input input);

    @AllArgsConstructor
    @Getter
    class Input {
        @Delegate
        private List<String> params;
    }

    @Getter
    @AllArgsConstructor
    class Output {
        @Delegate
        private List<String> result;
    }
}
