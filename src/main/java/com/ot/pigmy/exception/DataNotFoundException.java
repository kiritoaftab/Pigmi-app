package com.ot.pigmy.exception;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8378157149396540046L;

	private String message = "Data Not Present";

	public DataNotFoundException(String message) {
		this.message = message;
	}

	public DataNotFoundException() {

	}

	@Override
	public String toString() {
		return message;
	}
}