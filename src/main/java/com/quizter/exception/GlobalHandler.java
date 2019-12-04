package com.quizter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Collections;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity resourceNotFoundException(ResourceNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(Instant.now(), exception.getMessage(), Collections.emptyMap());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity tokenException(TokenException exception) {
        ErrorResponse errorResponse = new ErrorResponse(Instant.now(), exception.getMessage(), Collections.emptyMap());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NoUserWithThatIDException.class)
    public ResponseEntity noUserWithThatIDException(NoUserWithThatIDException exception) {
        ErrorResponse errorResponse = new ErrorResponse(Instant.now(), exception.getMessage(), Collections.emptyMap());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity validationHandler(ValidationException exception) {
        ErrorResponse errorResponse = new ErrorResponse(Instant.now(), exception.getMessage(), exception.getValidationErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}