package com.ot.pigmy.exception;

public class UserStatusIsOffline extends RuntimeException{
	
	private static final long serialVersionUID = -1318607899836323500L;
	
	private String message = "Invalid Credential";

	public UserStatusIsOffline(String message) {
		this.message = message;
	}

	public UserStatusIsOffline() {

	}

	@Override
	public String toString() {
		return message;
	}

}