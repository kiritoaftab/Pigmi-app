package com.ot.pigmy.dto;

import lombok.Data;

@Data
public class AccountNumberResp {
    String accountNumber;

    String accountType;

    double balance;

    String customerId;

    String customerName;
}
