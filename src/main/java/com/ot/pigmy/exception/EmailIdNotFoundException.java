package com.ot.pigmy.exception;

public class EmailIdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 393952239710983351L;
	
	private String message = "Email-Id Is Not Present";

	public EmailIdNotFoundException(String message) {
		this.message = message;
	}

	public EmailIdNotFoundException() {

	}

	@Override
	public String toString() {
		return message;
	}

}