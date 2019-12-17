package com.quizter.service.test;

import com.quizter.dictionary.QuestionType;
import com.quizter.dto.test.QuestionDto;
import com.quizter.dto.test.TestDto;
import com.quizter.entity.test.Question;
import com.quizter.entity.test.CodeQuestion;
import com.quizter.entity.test.MultiVariantQuestion;
import com.quizter.entity.test.Test;
import com.quizter.exception.ResourceNotFoundException;
import com.quizter.mapper.QuestionMapper;
import com.quizter.mapper.test.TestMapper;
import com.quizter.repository.QuestionRepository;
import com.quizter.repository.TestRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TestService<T extends Question> {

	TestRepository testRepository;

	QuestionRepository<Question> questionRepository;

	TestMapper<T> testMapper;

	QuestionMapper questionMapper;

	@Transactional(readOnly = true)
	public List<TestDto> findAllTest() {
		return testMapper.toTestListDto(testRepository.findAll());
	}

	@Transactional(readOnly = true)
	public TestDto findTestById(Long id) {
		return testMapper.toTestDto(testRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Test", "id", id)));
	}

	@Transactional
	public void createTest(TestDto testDto) {
		Test test = new Test();

		test.setName(testDto.getName());
		test.setDescription(testDto.getDescription());
		test.setSubject(testDto.getSubject());
		test.setQuestions(new ArrayList<>(createQuestions(testDto.getQuestions())));

		testRepository.save(test);
	}

	@Transactional
	public void updateTest(Long id, TestDto testDto) {
		Test test = testRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Test", "id", id));

		questionRepository.deleteAll(test.getQuestions());
		test.getQuestions().clear();

		test.setId(id);
		test.setName(testDto.getName());
		test.setSubject(testDto.getSubject());
		test.setDescription(testDto.getDescription());
		test.setQuestions(new ArrayList<>(createQuestions(testDto.getQuestions())));
		test.setDescription(testDto.getDescription());

		testRepository.save(test);
	}

	@Transactional
	public void deleteTest(Long id) {
		testRepository.deleteById(id);
	}

	@Transactional
	public List createQuestions(List<QuestionDto> questionDtos) {
		final MultiVariantQuestion[] multiVariantQuestion = new MultiVariantQuestion[1];
		final CodeQuestion[] codeQuestion = new CodeQuestion[1];
		return questionDtos.stream().filter(questionDto -> questionDto != null && questionDto.getQuestionType() != null).map(questionDto -> {
			if (questionDto.getQuestionType().equals(QuestionType.CODE)) {
				codeQuestion[0] = questionMapper.questionDtoToCodeQuestion(questionDto);
				questionRepository.save(codeQuestion[0]);
				return codeQuestion[0];
			} else {
				multiVariantQuestion[0] = questionMapper.questionDtoToMultivariantQuestion(questionDto);
				questionRepository.save(multiVariantQuestion[0]);
				return multiVariantQuestion[0];
			}

		}).collect(Collectors.toList());

	}

}