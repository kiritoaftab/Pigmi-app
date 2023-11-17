package com.ot.pigmy.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
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

	@Value("${aws.s3.bucketName}")
	private String bucketName;

	@Autowired
	private AmazonS3 amazonS3;

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

	public void generateTransactionCSV(HttpServletResponse response, String agentId, LocalDate localDate) {
		List<Transaction> list = transactionDao.findByAgentIdAndTransactionDate(agentId, localDate);
		if (list.size() > 0) {
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
							customer.getAddress(), 100, customerAccount.get().getBalance(), customer.getJoiningTime(),
							transac.getTransactionDate(), "", "", "", "", transac.getAmount(),
							transac.getLocalDateTime());
				}

				response.setContentType("text/csv");
				response.setHeader("Content-Disposition",
						"attachment; filename=transaction_" + agentId + "_" + localDate + ".csv");

				response.flushBuffer();
				csvPrinter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new DataNotFoundException("No Transaction Data Found");
		}

	}

	public void generateUserPDF(Transaction transaction, String pdfFileName) throws FileNotFoundException {

		Optional<CustomerAccount> customerAccount = customerAccountNumberRepository
				.findByAccountNumber(transaction.getAccountNumber());

		Customer customer = customerDao.findCustomerById(transaction.getCustomerId());

		Agent agent = agentDao.getAgentById(transaction.getAgentId());

		PdfWriter writer = new PdfWriter(pdfFileName);

		// Set custom page size with height=7 and width=5
		PageSize customPageSize = new PageSize(7 * 72, 5 * 72); // 72 points = 1 inch

		PdfDocument pdf = new PdfDocument(writer);
		pdf.setDefaultPageSize(customPageSize); // Set the custom page size

		Document document = new Document(pdf);
		document.setTextAlignment(TextAlignment.CENTER);

		// Get current date and time
		LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd    " + "    HH:mm:ss");
		String formattedDateTime = currentTime.format(formatter);

		// Add current time to the PDF
		Paragraph currentTimePara = new Paragraph();
		currentTimePara.add(" " + formattedDateTime + "\n" + "\n");
		currentTimePara.setTextAlignment(TextAlignment.RIGHT);

		Paragraph paragraph = new Paragraph();
		paragraph.add("UNION BANK " + "\n" + "\n");
		paragraph.setTextAlignment(TextAlignment.CENTER);

		// Add user details to the PDF
		Paragraph userDetails = new Paragraph();
		userDetails.add("Your Transaction Detail's : " + "\n" + "\n");
		userDetails.add("Amount Deposited	" + transaction.getAmount() + "\n");
		userDetails.add("Account Number		" + transaction.getAccountNumber() + "\n");
		userDetails.add("Account Type		" + transaction.getAccountType() + "\n");
		userDetails.add("Transaction time		" + transaction.getLocalDateTime() + "\n");
		userDetails.add("Your Account Balance		" + customerAccount.get().getBalance() + "\n");
		userDetails.add("Customer Name		" + customer.getCustomerName() + "\n");
		userDetails.add("Agent Name		" + agent.getAgentName() + "\n");
		userDetails.setTextAlignment(TextAlignment.LEFT);

		document.add(paragraph);
		document.add(currentTimePara);
		document.add(userDetails);

		document.close();
		pdf.close();
		System.out.println("PDF created successfully.");
	}

	public String uploadToUserS3Bucket(String pdfFileName1) {

		// Use user-specific bucket or folder structure in S3
		String userBucket = "awsbucket99999";
		String folderName = "pigmypdf";
		String key = folderName + "/" + pdfFileName1;

		// Upload file to user's S3 bucket
		amazonS3.putObject(userBucket, key, new File(pdfFileName1));

		return amazonS3.getUrl(userBucket, key).toString();

	}

}