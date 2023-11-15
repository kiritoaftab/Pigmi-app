package com.ot.pigmy.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ot.pigmy.dao.AgentDao;
import com.ot.pigmy.dao.CustomerAccountDao;
import com.ot.pigmy.dao.CustomerDao;
import com.ot.pigmy.dao.TransactionDao;
import com.ot.pigmy.dto.Agent;
import com.ot.pigmy.dto.Customer;
import com.ot.pigmy.dto.CustomerAccount;
import com.ot.pigmy.dto.ResponseStructure;
import com.ot.pigmy.dto.Transaction;
import com.ot.pigmy.dto.request.PrintTransactionDetails;
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
	private AgentDao agentDao;

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

	public ResponseEntity<ResponseStructure<PrintTransactionDetails>> findByTransactionId(long id) {
		ResponseStructure<PrintTransactionDetails> responseStructure = new ResponseStructure<>();
		Transaction transaction = transactionDao.findTransactionById(id);
		if (transaction != null) {
			PrintTransactionDetails printTransactionDetails = new PrintTransactionDetails();
			Customer customer = customerDao.findCustomerById(transaction.getCustomerId());
			Optional<CustomerAccount> customerAccount = customerAccountNumberRepository
					.findByAccountNumber(transaction.getAccountNumber());
			Agent agent = agentDao.getAgentById(transaction.getAgentId());
			if (customer != null) {
				printTransactionDetails.setCustomerId(customer.getId());
				printTransactionDetails.setCustomerName(customer.getCustomerName());
			} else {
				throw new IdNotFoundException("Customer Id Not Found");
			}
			if (customerAccount.isPresent()) {
				printTransactionDetails.setCustomerAccountBalance(customerAccount.get().getBalance());
			} else {
				throw new IdNotFoundException("Customer Account Number Not Found");
			}
			if (agent != null) {
				printTransactionDetails.setAgentId(agent.getId());
				printTransactionDetails.setAgentName(agent.getAgentName());
			} else {
				throw new IdNotFoundException("Customer Id Not Found");
			}
			printTransactionDetails.setTransaction(transaction);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Transaction Fetched Successfully");
			responseStructure.setData(printTransactionDetails);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Transaction Id Not Found");
		}
	}

//	public void generateTransactionExcel(HttpServletResponse response, LocalDate localDate) {
//		List<Transaction> list = transactionDao.findByTransactionDate(localDate);
//
//		HSSFWorkbook workbook = new HSSFWorkbook();
//		HSSFSheet sheet = workbook.createSheet("Transaction's Info");
//		HSSFRow row = sheet.createRow(0);
//
//		row.createCell(0).setCellValue("BRANCH ID");
//		row.createCell(1).setCellValue("AGENT CODE");
//		row.createCell(2).setCellValue("ACCOUNT TYPE");
//		row.createCell(3).setCellValue("ACCOUNT CODE");
//		row.createCell(4).setCellValue("ACCOUNTNO");
//		row.createCell(5).setCellValue("CUSTOMER ID");
//		row.createCell(6).setCellValue("ACCOUNT NAME");
//		row.createCell(7).setCellValue("LANDMARK");
//		row.createCell(8).setCellValue("AGREED AMOUNT");
//		row.createCell(9).setCellValue("BALANCE AMOUNT");
//		row.createCell(10).setCellValue("OPEN DATE");
//		row.createCell(11).setCellValue("LAST COLLECTION DATE");
//		row.createCell(12).setCellValue("LEAN ACCOUNT");
//		row.createCell(13).setCellValue("LEAN OPENDATE");
//		row.createCell(14).setCellValue("LEAN AMOUNT");
//		row.createCell(15).setCellValue("LEAN BALANCE");
//		row.createCell(16).setCellValue("COLLECTION AMOUNT");
//		row.createCell(17).setCellValue("COLLECTION DATETIME");
//
//		int dataRowIndex = 1;
//
//		for (Transaction transac : list) {
//			Customer customer = customerDao.findCustomerById(transac.getCustomerId());
//			Optional<CustomerAccount> customerAccount = customerAccountNumberRepository
//					.findByAccountNumber(transac.getAccountNumber());
//			HSSFRow dataRow = sheet.createRow(dataRowIndex);
//			dataRow.createCell(0).setCellValue(1);
//			dataRow.createCell(1).setCellValue(transac.getAgentId());
//			dataRow.createCell(2).setCellValue(transac.getAccountType());
//			dataRow.createCell(3).setCellValue(transac.getAccountCode());
//			dataRow.createCell(4).setCellValue(transac.getAccountNumber());
//			dataRow.createCell(5).setCellValue(transac.getCustomerId());
//			dataRow.createCell(6).setCellValue(customer.getCustomerName());
//			dataRow.createCell(7).setCellValue(customer.getAddress());
//			dataRow.createCell(8).setCellValue(transac.getAmount());
//			dataRow.createCell(9).setCellValue(customerAccount.get().getBalance());
//			dataRow.createCell(10).setCellValue(customer.getJoiningTime());
//			dataRow.createCell(11).setCellValue(transac.getTransactionDate());
//			dataRow.createCell(12).setCellValue("");
//			dataRow.createCell(13).setCellValue("");
//			dataRow.createCell(14).setCellValue("");
//			dataRow.createCell(15).setCellValue("");
//			dataRow.createCell(16).setCellValue("");
//			dataRow.createCell(17).setCellValue("");
//			dataRowIndex++;
//		}
//		try {
//
//			ServletOutputStream ops = response.getOutputStream();
//			workbook.write(ops);
//			workbook.close();
//			ops.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public void generateTransactionCSV(HttpServletResponse response,String agentId, LocalDate localDate) {
		List<Transaction> list = transactionDao.findByAgentIdAndTransactionDate(agentId,localDate);

		try (PrintWriter writer = response.getWriter();
				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

			// Add CSV header
			csvPrinter.printRecord("BRANCH ID", "AGENT CODE", "ACCOUNT TYPE", "ACCOUNT CODE", "ACCOUNTNO",
					"CUSTOMER ID", "ACCOUNT NAME", "LANDMARK", "AGREED AMOUNT", "BALANCE AMOUNT", "OPEN DATE",
					"LAST COLLECTION DATE", "LEAN ACCOUNT", "LEAN OPENDATE", "LEAN AMOUNT", "LEAN BALANCE",
					"COLLECTION AMOUNT", "COLLECTION DATETIME");

			for (Transaction transac : list) {
				Customer customer = customerDao.findCustomerById(transac.getCustomerId());
				Optional<CustomerAccount> customerAccount = customerAccountNumberRepository
						.findByAccountNumber(transac.getAccountNumber());

				csvPrinter.printRecord(1, transac.getAgentId(), transac.getAccountType(), transac.getAccountCode(),
						transac.getAccountNumber(), transac.getCustomerId(), customer.getCustomerName(),
						customer.getAddress(), 100, customerAccount.get().getBalance(),
						customer.getJoiningTime(), transac.getTransactionDate(), "", "", "", "", transac.getAmount(), transac.getLocalDateTime());
			}

			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=transaction_"+agentId+"_" + localDate + ".csv");

			response.flushBuffer();
			csvPrinter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
