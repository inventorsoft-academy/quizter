package com.quizter.service.test;

import com.quizter.dto.test.CodingQuestionDto;
import com.quizter.dto.test.MultivariantQuestionDto;
import com.quizter.entity.test.CodeQuestion;
import com.quizter.mapper.test.TestMapper;
import com.quizter.repository.QuestionRepository;
import com.quizter.repository.TestRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class TestServiceTest {

	@Mock
	TestRepository testRepository;

	@Mock
	QuestionRepository questionRepository;

	@Mock
	TestMapper testMapper;

	@InjectMocks
	TestService testService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreation() {
		List list = new ArrayList();

		list.add(new MultivariantQuestionDto());
		list.add(new MultivariantQuestionDto());
		list.add(new MultivariantQuestionDto());
		list.add(new MultivariantQuestionDto());
		list.add(new MultivariantQuestionDto());
		list.add(new CodingQuestionDto());
		list.add(new CodingQuestionDto());
		list.add(new CodingQuestionDto());
		list.add(new CodingQuestionDto());
		list.add(new CodingQuestionDto());
		list.stream().forEach(e -> {
		if(e instanceof MultivariantQuestionDto){
			System.out.println("MultivariantQuestionDto");
		}
		});
		//		testService.createQuestions(list);
	}

}