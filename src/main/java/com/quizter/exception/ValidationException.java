package com.quizter.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class ValidationException extends RuntimeException {

    Map<String, String> validationErrors;

    public ValidationException(Map<String, String> message) {
        super("Validation error");
        this.validationErrors = message;
    }
}