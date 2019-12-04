package com.quizter.util;


import java.util.List;

public class TestTest {

    public void test() {
        TestClass testClass = new TestClass();
        TestCase.Output process = testClass.process(new TestCase.Input(List.of("1", "2")));

        assert process.get(0) == "qwe";
    }
}
