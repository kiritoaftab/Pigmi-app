package com.ot.pigmy.repository;

import com.ot.pigmy.dto.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    public Transaction findByCustomerIdAndAgentIdAndTransactionDate(String customerId, String agentId, LocalDate localDate);
}
