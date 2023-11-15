package com.ot.pigmy.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ot.pigmy.dto.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	public Transaction findByCustomerIdAndAgentIdAndTransactionDate(String customerId, String agentId,
			LocalDate localDate);

	public Optional<List<Transaction>> findByCustomerId(String query);

	public Optional<List<Transaction>> findByAgentId(String query);

	public Optional<List<Transaction>> findByTransactionDate(LocalDate localDate);

	public Optional<List<Transaction>> findByAgentIdAndTransactionDate(String agentId,LocalDate localDate);
}