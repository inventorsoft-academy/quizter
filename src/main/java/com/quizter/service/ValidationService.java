package com.quizter.service;

import com.quizter.dto.PasswordDto;
import com.quizter.dto.RegistrationUserDto;
import com.quizter.dto.test.QuestionDto;
import com.quizter.dto.test.TestDto;
import com.quizter.exception.ValidationException;
import com.quizter.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidationService {

    UserRepository userRepository;

    Validator validator;

    private <T> Map<String, String> validate(T t) {

        Map<String, String> errors = new HashMap<>();

        if (t == null) {
            errors.put("ValidationError", "Data is empty");
            return errors;
        }

        Set<ConstraintViolation<T>> constraintViolation = validator.validate(t);

        if (constraintViolation != null && !constraintViolation.isEmpty()) {
            constraintViolation.forEach(x -> errors.put(x.getPropertyPath().toString() + "Error", x.getMessage()));
        }

        return errors;
    }

    public void registrationValidation(RegistrationUserDto registrationUserDto) {

        Map<String, String> validationResult = validate(registrationUserDto);
        passwordValidation(registrationUserDto.getPassword());
        if (userRepository.findByEmail(registrationUserDto.getEmail()).isPresent()) {
            validationResult.put("EmailError", "User with such email already exists");
        }
        handle(validationResult);
    }

    public void passwordValidation(PasswordDto passwordDto) {
        Map<String, String> validationResult = validate(passwordDto);
        handle(validationResult);
    }

    private void handle(Map<String, String> validationResult) {
        if (!validationResult.isEmpty()) {
            throw new ValidationException(validationResult);
        }
    }

    public void testCreationFormValidation(TestDto testDto) {
        Map<String, String> validationResult = validate(testDto);

        List<QuestionDto> multivariantQuestionList = testDto.getQuestions()
                .stream()
                .filter(questionDto -> questionDto.getQuestionType().name().equals("MULTIVARIANT")).collect(Collectors.toList());

        List<QuestionDto> codeQuestionList = testDto.getQuestions()
                .stream()
                .filter(questionDto -> questionDto.getQuestionType().name().equals("CODE")).collect(Collectors.toList());

        if (testDto.getName().length() < 5 || testDto.getSubject().length() < 2 || testDto.getDescription().length() < 10) {
            validationResult.put("TestCreationFormError", "Some lengths of the test parameters are too small");
            handle(validationResult);
        } else if (testDto.getName().equals(testDto.getSubject()) || testDto.getName().equals(testDto.getDescription()) || testDto.getDescription().equals(testDto.getSubject())) {
            validationResult.put("TestCreationFormError", "Test parameters must be a difference");
            handle(validationResult);
        } else if (testDto.getDuration() < 5 || testDto.getDuration() > 180) {
            validationResult.put("TestCreationFormError", "Test duration interval ought to be from 5 to 180 minutes");
            handle(validationResult);
        } else if (multivariantQuestionList.size() < 1) {
            validationResult.put("TestCreationFormError", "Question quantity must be at least 5");
            handle(validationResult);
        } else if (multivariantQuestionList.stream().anyMatch(questionDto -> questionDto.getName().equals(""))) {
            validationResult.put("TestCreationFormError", "Question name is not set");
            handle(validationResult);
        } else if (codeQuestionList.stream().anyMatch(questionDto -> questionDto.getName().equals(""))) {
            validationResult.put("TestCreationFormError", "Code task name is not set");
            handle(validationResult);
        } else if (multivariantQuestionList.stream().anyMatch(questionDto -> questionDto.getAnswers().keySet().contains(""))) {
            validationResult.put("TestCreationFormError", "Some answer name is not set");
            handle(validationResult);
        }

        multivariantQuestionList
                .forEach(questionDto -> {
                    if (questionDto.getAnswers().keySet().size() < 4) {
                        validationResult.put("TestCreationFormError", "Question answers must be a difference");
                    }

                    handle(validationResult);

                    int countOfRightAnswer = (int) questionDto.getAnswers()
                            .values()
                            .stream()
                            .filter(Boolean::booleanValue).count();

                    if (countOfRightAnswer != 1) {
                        validationResult.put("TestCreationFormError", "Right answer is not set");
                    }

                    handle(validationResult);
                });
    }

    public void validateVersion(String versionFromDto, String versionFromDB) {
        Map<String, String> validationResult = validate(versionFromDto);

        if (versionFromDto.equals(versionFromDB)) {
            validationResult.put("TestCreationFormError", "Please set new version");
        }

        handle(validationResult);
    }

}