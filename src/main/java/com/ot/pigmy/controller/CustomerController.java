package com.ot.pigmy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ot.pigmy.dto.Customer;
import com.ot.pigmy.dto.CustomerAccount;
import com.ot.pigmy.dto.ResponseStructure;
import com.ot.pigmy.dto.request.AddAccount;
import com.ot.pigmy.service.CustomerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@ApiOperation(value = "Save Customer", notes = "Input is Customer Object and return Customer object")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 409, message = "Customer Already Exist") })
	@PostMapping(value = "/save", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Customer>> saveCustomer(@RequestBody @Validated Customer customer) {
		return customerService.saveCustomer(customer);
	}

	@ApiOperation(value = "Add Account to Customer", notes = "Input is Account Object and return Customer Account object")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 409, message = "Account Already Exist") })
	@PostMapping(value = "/addAccount", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<CustomerAccount>> saveCustomerAccount(@RequestBody AddAccount request) {
		return customerService.saveAccount(request);
	}

	@ApiOperation(value = "Fetch All Customers", notes = "Return The List Of Customers")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Customers Object") })
	@GetMapping(value = "/getAllCustomers", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Customer>>> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	@ApiOperation(value = "Fetch Customer by id", notes = "Input Is Id Of The Customer Object and return Customer Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/id/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Customer>> findCustomerById(@PathVariable String id) {
		return customerService.getCustomerById(id);
	}

	@ApiOperation(value = "Fetch Customers by id/ Name", notes = "Input Is Id/Name Of The Customer Object and return Customer Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/query/{query}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Customer>>> findCustomersByQuery(@PathVariable String query) {
		return customerService.searchQuery(query);
	}

	@ApiOperation(value = "Fetch Customers by Agent Id And Name", notes = "Input Fetch Customers by Agent Id And Name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/findByAgendIdAndCustomerName", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Customer>>> findByAgendIdAndCustomerName(@RequestParam String agentId,
			@RequestParam String query) {
		return customerService.findAgendIdAndCustomerName(agentId, query);
	}

	@ApiOperation(value = "Fetch All Customers With Pagination And Sort", notes = "Return The List Of Customers With Pagination And Sort")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Customers Object") })
	@GetMapping(value = "/getAllCustomers/{offset}/{pageSize}/{field}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Page<Customer>>> getCustomersWithPaginationAndSort(@PathVariable int offset,
			@PathVariable int pageSize, @PathVariable String field) {
		return customerService.getCustomersWithPaginationAndSorting(offset, pageSize, field);
	}

	@ApiOperation(value = "Delete Customer Object", notes = "Input Is Id Of The Customer Object And Output Is String")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Deleted"),
			@ApiResponse(code = 404, message = "Not Found") })
	@DeleteMapping(value = "/deleteCustomer/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<String>> deleteCustomerById(@PathVariable String id) {
		return customerService.deleteCustomerById(id);
	}

	@ApiOperation(value = "Update Customer Object", notes = "Input Is Customer Object And Return Updated Customer Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PutMapping(value = "/updateCustomer", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Customer>> updateAgent(@RequestBody @Validated Customer customer) {
		return customerService.updateCustomer(customer);
	}

	@ApiOperation(value = "With Drawal Of Amount ", notes = "Input Is id, accountNumber, withdrawAmount")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PutMapping(value = "/withDrawalOfAmount", consumes = { MediaType.ALL_VALUE }, produces = { MediaType.ALL_VALUE })
	public ResponseEntity<String> withDrawalOfAmount(@RequestParam String id, @RequestParam String accountType,
			@RequestParam double withdrawAmount) {
		return customerService.withDrawalOfAmount(id, accountType, withdrawAmount);
	}

}