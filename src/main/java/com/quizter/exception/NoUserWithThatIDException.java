package com.quizter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoUserWithThatIDException extends ResourceNotFoundException {

    public NoUserWithThatIDException(String resourceName, String fieldName, Object fieldValue) {
        super(resourceName, fieldName, fieldValue);
    }

}
