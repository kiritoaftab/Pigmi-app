package com.ot.pigmy.dto.request;

import com.ot.pigmy.dto.Transaction;

import lombok.Data;

@Data
public class PrintTransactionDetails {
	private Transaction transaction;
	private String customerId;
	private String customerName;
	private double customerAccountBalance;
	private String agentId;
	private String agentName;
}