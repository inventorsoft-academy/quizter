package com.quizter.annotation;

import com.quizter.dto.RegistrationUserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidation implements ConstraintValidator<PasswordMatches, RegistrationUserDto> {

    @Override
    public boolean isValid(RegistrationUserDto user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getConfirmPassword().equals(user.getPassword());
    }
}
