package com.quizter.exception;

public class NoUserWithThatIDException extends ResourceNotFoundException {

    public NoUserWithThatIDException(String resourceName, String fieldName, Object fieldValue) {
        super(resourceName, fieldName, fieldValue);
    }

}
