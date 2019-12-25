package com.quizter.service.test;

import com.quizter.entity.test.Question;
import com.quizter.repository.QuestionRepository;
import com.quizter.util.UnZipUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

class QuizResultServiceTest {

	@Test
	void testCodeAnswer() throws InterruptedException, IOException, TimeoutException {
		QuizResultService.testCodeAnswer();
	}
	@Test
	public void unzip(){
		UnZipUtil.unZip();
	}
}