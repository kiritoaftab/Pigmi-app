package com.ot.pigmy.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.ot.pigmy.dto.Transaction;
import com.ot.pigmy.repository.TransactionRepository;

@Repository
public class TransactionDao {
	@Autowired
	private TransactionRepository transactionRepository;

	public Transaction saveTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	public Page<Transaction> findAgentsWithPaginationAndSorting(int offset, int pageSize, String field) {
		Page<Transaction> transactions = transactionRepository
				.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
		return transactions;
	}

	public List<Transaction> findByCustomerId(String query) {
		Optional<List<Transaction>> transactions = transactionRepository.findByCustomerId(query);
		return transactions.orElse(null);
	}

	public List<Transaction> findByAgentId(String query) {
		Optional<List<Transaction>> transactions = transactionRepository.findByAgentId(query);
		return transactions.orElse(null);
	}

	public boolean verifyTransaction(String customerId, String agentId, LocalDate date) {
		Transaction transaction = transactionRepository.findByCustomerIdAndAgentIdAndTransactionDate(customerId,
				agentId, date);
		return Objects.isNull(transaction);
	}

	public List<Transaction> findByTransactionDate(LocalDate localDate) {
		Optional<List<Transaction>> transactions = transactionRepository.findByTransactionDate(localDate);
		return transactions.orElse(null);
	}

}
