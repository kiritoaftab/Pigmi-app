package com.ot.pigmy.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ot.pigmy.dao.CustomerAccountDao;
import com.ot.pigmy.dao.CustomerDao;
import com.ot.pigmy.dao.TransactionDao;
import com.ot.pigmy.dto.CustomerAccount;
import com.ot.pigmy.dto.ResponseStructure;
import com.ot.pigmy.dto.Transaction;
import com.ot.pigmy.dto.request.TransactionResp;
import com.ot.pigmy.exception.DataNotFoundException;
import com.ot.pigmy.exception.DuplicateDataEntryException;
import com.ot.pigmy.exception.IdNotFoundException;
import com.ot.pigmy.repository.CustomerAccountNumberRepository;
import com.ot.pigmy.util.EmailSender;

@Service
public class TransactionService {
	@Autowired
	private TransactionDao transactionDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private CustomerAccountDao customerAccountDao;

	@Autowired
	private CustomerAccountNumberRepository customerAccountNumberRepository;
	@Autowired
	private EmailSender emailSender;

	public ResponseEntity<ResponseStructure<TransactionResp>> saveTransaction(Transaction transaction) {

		ResponseStructure<TransactionResp> responseStructure = new ResponseStructure<>();

		if (!customerAccountNumberRepository.findByAccountNumber(transaction.getAccountNumber()).isPresent()) {
			throw new IdNotFoundException("Account number " + transaction.getAccountNumber() + " does not exist");
		}
		if (customerDao.findByCustomerIdAndAgentId(transaction.getCustomerId(), transaction.getAgentId()) == null) {
			throw new IdNotFoundException(
					"Customer Id " + transaction.getCustomerId() + " does not belong to " + transaction.getAgentId());
		}

		if (!transactionDao.verifyTransaction(transaction.getCustomerId(), transaction.getAgentId(), LocalDate.now())) {
			throw new DuplicateDataEntryException("Transaction already done for this Customer "
					+ transaction.getCustomerId() + ", for today " + LocalDate.now().toString());
		}

		CustomerAccount customerAccount = customerAccountDao.addAmount(transaction.getAmount(),
				transaction.getAccountNumber());
		transaction = transactionDao.saveTransaction(transaction);

		TransactionResp transactionResp = new TransactionResp();
		transactionResp.setTransaction(transaction);
		transactionResp.setCustomer(customerAccount.getCustomer());

		emailSender.sendSimpleEmail(
				customerAccount.getCustomer().getEmail(), "Hello " + customerAccount.getCustomer().getCustomerName()
						+ ", amount of Rs. " + transaction.getAmount() + " has been transferred to your account ",
				"Transaction Completed");
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("Transaction Saved Successfully");
		responseStructure.setData(transactionResp);
		return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);

	}

	public ResponseEntity<ResponseStructure<Page<Transaction>>> getTransactionsWithPaginationAndSorting(int offset,
			int pageSize, String field) {
		ResponseStructure<Page<Transaction>> responseStructure = new ResponseStructure<>();
		Page<Transaction> page = transactionDao.findAgentsWithPaginationAndSorting(offset, pageSize, field);
		if (page.getSize() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Agents Fetched");
			responseStructure.setRecordCount(page.getSize());
			responseStructure.setData(page);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Transaction's Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<List<Transaction>>> findByCustomerIdAndAgentIdContaining(String query) {

		ResponseStructure<List<Transaction>> responseStructure = new ResponseStructure<>();

		if (query.matches(".*\\d.*") && query.startsWith("C")) {
			List<Transaction> transactions = transactionDao.findByCustomerId(query);
			if (transactions.isEmpty()) {
				throw new IdNotFoundException("Id Not Found " + query);
			}
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetch Transaction By Id");
			responseStructure.setData(transactions);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else if (query.matches(".*\\d.*") && query.startsWith("A")) {
			List<Transaction> transactions = transactionDao.findByAgentId(query);
			if (transactions.isEmpty()) {
				throw new IdNotFoundException("Id Not Found " + query);
			}
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetch Transaction By Id");
			responseStructure.setData(transactions);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("No Such Transaction Found ");
		}

	}

	public ResponseEntity<ResponseStructure<List<Transaction>>> findByTransactionDate(LocalDate localDate) {
		ResponseStructure<List<Transaction>> responseStructure = new ResponseStructure<>();
		List<Transaction> list = transactionDao.findByTransactionDate(localDate);
		if (list.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetch Transaction By DATE");
			responseStructure.setData(list);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("No Such Transaction Found ");
		}

	}
}