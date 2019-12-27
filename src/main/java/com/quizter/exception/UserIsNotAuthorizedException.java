package com.quizter.exception;

public class UserIsNotAuthorizedException extends RuntimeException {

	public UserIsNotAuthorizedException() {
		super("User is not authorized");
	}
}
