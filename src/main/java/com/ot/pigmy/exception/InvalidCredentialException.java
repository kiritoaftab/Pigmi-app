package com.ot.pigmy.exception;

public class InvalidCredentialException extends RuntimeException{

	private static final long serialVersionUID = -4298938161723037264L;
	
	private String message = "Invalid Credential";

	public InvalidCredentialException(String message) {
		this.message = message;
	}

	public InvalidCredentialException() {

	}

	@Override
	public String toString() {
		return message;
	}

}