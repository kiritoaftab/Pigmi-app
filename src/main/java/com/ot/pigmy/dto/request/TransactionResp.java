package com.ot.pigmy.dto.request;

import com.ot.pigmy.dto.Customer;
import com.ot.pigmy.dto.Transaction;

import lombok.Data;

@Data
public class TransactionResp {
    private Transaction transaction;
    private Customer customer;

}
