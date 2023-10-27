package com.ot.pigmy.dto;

import lombok.Data;

@Data
public class ResponseStructure<T> {

	private int status;
	
	private int recordCount;

	private String message;

	private T data;

	

	
}