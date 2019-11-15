package com.quizter.exception;

public class PasswordConfirmException extends RuntimeException{
    public PasswordConfirmException(String message) {
        super(message);
    }
}
