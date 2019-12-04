package com.quizter.annotation;

import com.quizter.dto.PasswordDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidation implements ConstraintValidator<PasswordMatches, PasswordDto> {

    @Override
    public boolean isValid(PasswordDto user, ConstraintValidatorContext constraintValidatorContext) {

        if (!user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!^&+=])(?=\\S+$).{8,}$")) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "Password is weak").addPropertyNode("Password").addConstraintViolation();
            return false;
        } else
            constraintValidatorContext.buildConstraintViolationWithTemplate("Password mismatch").
                    addPropertyNode("Password").addConstraintViolation();
        ;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
