package com.quizter.annotation;

import com.quizter.dto.PasswordDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidation implements ConstraintValidator<PasswordMatches, PasswordDto> {

    @Override
    public boolean isValid(PasswordDto user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getConfirmPassword().equals(user.getPassword());
    }
}
