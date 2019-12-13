package com.quizter.service.test;

import com.quizter.dto.test.QuestionDto;
import com.quizter.dto.test.TestDto;
import com.quizter.entity.test.MultiVariantQuestion;
import com.quizter.entity.test.Test;
import com.quizter.exception.ResourceNotFoundException;
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

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TestService {

    TestRepository testRepository;

    QuestionRepository questionRepository;

    TestMapper testMapper;

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
        test.setQuestions(createQuestions(testDto.getQuestions()));

        testRepository.save(test);
    }

    @Transactional
    public void updateTest(Long id, TestDto testDto) {
        Test test = testRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Test", "id", id));

        questionRepository.deleteAll(test.getQuestions());
        test.getQuestions().clear();

        test.getQuestions().clear();

        test.setId(id);
        test.setName(testDto.getName());
        test.setSubject(testDto.getSubject());
        test.setDescription(testDto.getDescription());
        test.setQuestions(createQuestions(testDto.getQuestions()));
        test.setDescription(testDto.getDescription());

        testRepository.save(test);
    }

    @Transactional
    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }

    @Transactional
    public List<MultiVariantQuestion> createQuestions(List<QuestionDto> questionDtos) {
        List<MultiVariantQuestion> questions = testMapper.toQuestionList(questionDtos);

        List<MultiVariantQuestion> savedQuestions = new ArrayList<>();

        questions.forEach(e -> {
                    MultiVariantQuestion question = new MultiVariantQuestion();
                    question.setName(e.getName());
                    question.setAnswers(e.getAnswers());

                    questionRepository.save(question);

                    savedQuestions.add(question);
                }
        );

        return savedQuestions;
    }

}
