package com.ot.pigmy.exception;

public class IdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -396952154138812499L;
	
	private String message = "Id Is Not Present";

	public IdNotFoundException(String message) {
		this.message = message;
	}

	public IdNotFoundException() {

	}

	@Override
	public String toString() {
		return message;
	}
}