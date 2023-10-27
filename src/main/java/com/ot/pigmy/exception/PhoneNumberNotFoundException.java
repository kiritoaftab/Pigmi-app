package com.ot.pigmy.exception;

public class PhoneNumberNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 512863331724122837L;
	
	private String message = "Phone Number Is Not Present";

	public PhoneNumberNotFoundException(String message) {
		this.message = message;
	}

	public PhoneNumberNotFoundException() {

	}

	@Override
	public String toString() {
		return message;
	}

}