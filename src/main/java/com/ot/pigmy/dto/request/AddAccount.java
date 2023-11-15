package com.ot.pigmy.dto.request;

import lombok.Data;

@Data
public class AddAccount {
    String accountNumber;
    String accountType;
    String customerId;
    double balance;
    String accountCode;
}
