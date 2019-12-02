package com.quizter.service.testservice;

import com.quizter.dto.request.TestWizardRequest;
import com.quizter.entity.test.Test;
import com.quizter.exception.ResourceNotFoundException;
import com.quizter.repository.TestRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TestService {

    QuestionService questionService;

    TestRepository testRepository;

    @Transactional(readOnly = true)
    public List<Test> findAllTest() {
        return testRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Test findTestById(Long id) {
        return testRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Test", "id", id));
    }

    @Transactional
    public void createTest(TestWizardRequest testWizardRequest) {
        Test test = new Test();

        test.setName(testWizardRequest.getName());
        test.setDescription(testWizardRequest.getDescription());
        test.setSubject(testWizardRequest.getSubject());
        test.setQuestions(questionService.createQuestions(testWizardRequest.getQuestions()));

        testRepository.save(test);
    }

    @Transactional
    public Test updateTest(Long id, TestWizardRequest testWizardRequest) {
        Test test = testRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Test", "id", id));

        test.setId(id);
        test.setName(testWizardRequest.getName());
        test.setSubject(testWizardRequest.getSubject());
        test.setQuestions(testWizardRequest.getQuestions());

        return testRepository.save(test);
    }

    @Transactional
    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }
}
