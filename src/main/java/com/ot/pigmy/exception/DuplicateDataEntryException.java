package com.ot.pigmy.exception;

public class DuplicateDataEntryException extends RuntimeException {

	private static final long serialVersionUID = 7942553504088680241L;

	private String message = "This Data Is Already Exists";

	public DuplicateDataEntryException(String message) {
		this.message = message;
	}

	public DuplicateDataEntryException() {

	}

	@Override
	public String toString() {
		return message;
	}
}