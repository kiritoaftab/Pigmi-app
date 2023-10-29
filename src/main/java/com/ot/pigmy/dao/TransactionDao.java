package com.ot.pigmy.dao;

import com.ot.pigmy.dto.Transaction;
import com.ot.pigmy.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Objects;

@Repository
public class TransactionDao {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction saveTransaction(Transaction transaction){

        return  transactionRepository.save(transaction);
    }

    public boolean verifyTransaction(String customerId, String agentId, LocalDate date){
        Transaction transaction = transactionRepository.findByCustomerIdAndAgentIdAndTransactionDate(customerId,agentId,date);
        return Objects.isNull(transaction);
    }

}
