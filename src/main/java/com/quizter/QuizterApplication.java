package com.quizter;

import com.quizter.util.AppConstants;
import com.quizter.util.UnZipUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableConfigurationProperties(AppConstants.class)
public class QuizterApplication {

	public static void main(String[] args) throws InterruptedException, IOException, TimeoutException {
		SpringApplication.run(QuizterApplication.class, args);
	}

}
