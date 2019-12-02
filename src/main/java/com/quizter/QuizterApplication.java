package com.quizter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuizterApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizterApplication.class, args);
    }

}
