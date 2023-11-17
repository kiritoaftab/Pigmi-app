package com.ot.pigmy.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ot.pigmy.dto.ResponseStructure;
import com.ot.pigmy.dto.Transaction;
import com.ot.pigmy.dto.request.PrintTransactionDetails;
import com.ot.pigmy.dto.request.TransactionResp;
import com.ot.pigmy.repository.TransactionRepository;
import com.ot.pigmy.service.TransactionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "*")
public class TransactionController {
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private TransactionRepository transactionRepository;

	@ApiOperation(value = "Save Transaction", notes = "Input is Transaction Object and return TransactionResp object")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 409, message = "Transaction Already Exist") })
	@PostMapping(value = "/save", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<TransactionResp>> saveCustomer(@RequestBody Transaction transaction) {
		return transactionService.saveTransaction(transaction);
	}

	@ApiOperation(value = "Fetch All Transactions With Pagination And Sort", notes = "Return The List Of Transactions With Pagination And Sort")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Transactions Object") })
	@GetMapping(value = "/getAllTransactions/{offset}/{pageSize}/{field}", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Page<Transaction>>> getTransactionsWithPaginationAndSort(
			@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field) {
		return transactionService.getTransactionsWithPaginationAndSorting(offset, pageSize, field);
	}

	@ApiOperation(value = "Fetch Transaction By Customer Id Or Agent Id", notes = "Input is Customer Id Or Agent Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS") })
	@GetMapping(value = "/findTransactionByCustomerIdORAgentId", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Transaction>>> findByCustomerIdAndAgentIdContaining(
			@RequestParam String query) {
		return transactionService.findByCustomerIdAndAgentIdContaining(query);
	}

	@ApiOperation(value = "Fetch Transaction By Date", notes = "Input is Date")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS") })
	@GetMapping(value = "/findByTransactionDate", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Transaction>>> findByTransactionDate(
			@RequestParam LocalDate localDate) {
		return transactionService.findByTransactionDate(localDate);
	}

	@ApiOperation(value = "Fetch Transaction By Id", notes = "Input is Long Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS") })
	@GetMapping(value = "/findByTransactionId/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<PrintTransactionDetails>> findByTransactionId(@PathVariable long id) {
		return transactionService.findByTransactionId(id);
	}

	@GetMapping("/csv/{agentId}/{localDate}")
	public void generateCSVReport(HttpServletResponse response, @PathVariable String agentId,
			@PathVariable LocalDate localDate) {
		transactionService.generateTransactionCSV(response, agentId, localDate);
	}

	@GetMapping("/generateAndUploadPDF/{id}")
	public String generateAndUploadPDF(@PathVariable long id) {
		try {
			// Fetch user details from the database
			Optional<Transaction> transaction = transactionRepository.findById(id);
			if (transaction.isPresent()) {
				// Generate PDF for the user
				String pdfFileName = "AgentId "+transaction.get().getAgentId() + ".pdf";
				transactionService.generateUserPDF(transaction.get(), pdfFileName);

				// Upload PDF to the user's AWS S3 bucket
				transactionService.uploadToUserS3Bucket(pdfFileName);

				return "PDF generated and uploaded to user's AWS S3 bucket successfully!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error generating or uploading PDF!";
		}
		return null;
	}
}