package com.quizter.service;

import com.quizter.dto.AvatarDto;
import com.quizter.dto.PasswordDto;
import com.quizter.dto.ProfileDto;
import com.quizter.dto.RegistrationUserDto;
import com.quizter.dto.test.QuestionDto;
import com.quizter.dto.test.TestDto;
import com.quizter.dto.test.TestEditDto;
import com.quizter.exception.ValidationException;
import com.quizter.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
@Slf4j
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

        testFormValidation(validationResult, testDto.getQuestions());
    }

    public void testEditionFormValidation(TestEditDto testEditDto) {
        Map<String, String> validationResult = validate(testEditDto);

        testFormValidation(validationResult, testEditDto.getQuestions());
    }

    private void testFormValidation(Map<String, String> validationResult, List<QuestionDto> questionDtos) {

        if (!validationResult.isEmpty()) {
            handle(validationResult);
        } else {
            List<QuestionDto> multivariantQuestionList = questionDtos
                    .stream()
                    .filter(questionDto -> questionDto.getQuestionType().name().equals("MULTIVARIANT")).collect(Collectors.toList());

            if (multivariantQuestionList.size() < 1) {
                validationResult.put("TestCreationFormError", "Question quantity should have at least 5");
                handle(validationResult);
            } else if (multivariantQuestionList.stream().anyMatch(questionDto -> questionDto.getAnswers().keySet().contains(""))) {
                validationResult.put("TestCreationFormError", "Some answer name is not set");
                handle(validationResult);
            }

            multivariantQuestionList
                    .forEach(questionDto -> {
                        if (questionDto.getAnswers().keySet().size() < 4) {
                            validationResult.put("TestCreationFormError", "Question answers must be different");
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
    }

}