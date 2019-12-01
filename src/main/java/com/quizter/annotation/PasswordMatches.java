package com.quizter.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidation.class)
public @interface PasswordMatches {

    String message() default "Passwords is incorrect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
