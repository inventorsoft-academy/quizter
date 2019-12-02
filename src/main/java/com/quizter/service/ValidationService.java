package com.quizter.service;

import com.quizter.dto.PasswordDto;
import com.quizter.dto.RegistrationUserDto;
import com.quizter.exception.ValidationException;
import com.quizter.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Transactional
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidationService {

    UserRepository userRepository;

    private <T> Map<String, String> validate(T t) {

        Map<String, String> errors = new HashMap<>();

        if (t == null) {
            errors.put("ValidationError", "Data is empty");
            return errors;
        }

        Set<ConstraintViolation<T>> constraintViolation = validator().validate(t);

        if (constraintViolation != null && constraintViolation.size() > 0) {
            constraintViolation.stream().forEach(x -> errors.put(x.getPropertyPath().toString() + "Error", x.getMessage()));
        }

        return errors;

    }

    public void registrationValidation(RegistrationUserDto registrationUserDto) {

        Map<String, String> validationResult = validate(registrationUserDto);
        if (userRepository.findByEmail(registrationUserDto.getEmail()).isPresent()) {
            validationResult.put("EmailError", "User with such email already exists");
        }
        handle(validationResult);
    }

    public void passwordValidation(PasswordDto passwordDto){
        Map<String, String> validationResult = validate(passwordDto);
        handle(validationResult);
    }

    private void handle(Map<String, String> validationResult) {
        if (!validationResult.isEmpty()) {
            throw new ValidationException(validationResult);
        }
    }

    public void loginValidation(){

    }
    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

}