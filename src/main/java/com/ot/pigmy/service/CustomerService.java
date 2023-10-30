package com.ot.pigmy.service;

import com.ot.pigmy.dao.AgentDao;
import com.ot.pigmy.dao.CustomerDao;
import com.ot.pigmy.dto.Customer;
import com.ot.pigmy.dto.CustomerAccount;
import com.ot.pigmy.dto.ResponseStructure;
import com.ot.pigmy.dto.request.AddAccount;
import com.ot.pigmy.exception.DataNotFoundException;
import com.ot.pigmy.exception.DuplicateDataEntryException;
import com.ot.pigmy.exception.IdNotFoundException;
import com.ot.pigmy.repository.CustomerAccountNumberRepository;
import com.ot.pigmy.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private CustomerAccountNumberRepository customerAccountNumberRepository;
	@Autowired
	private EmailSender emailSender;

	@Autowired
	private AgentDao agentDao;

	public ResponseEntity<ResponseStructure<Customer>> saveCustomer(Customer customer) {

		ResponseStructure<Customer> responseStructure = new ResponseStructure<>();

		if (customerDao.findByCustomerEmail(customer.getEmail()) != null
				|| customerDao.findByCustomerPhone(customer.getPhone()) != null) {
			throw new DuplicateDataEntryException("Customer Already Exists " + customer.getCustomerName());
		} else if (agentDao.getAgentById(customer.getAgentId()) == null) {
			throw new DuplicateDataEntryException("Agent Does not exist" + customer.getAgentId());
		} else {
			String uuid = UUID.randomUUID().toString();
			String partOfUuid = uuid.substring(0, 11);
			if (partOfUuid.contains("-")) {
				String replace = partOfUuid.replace("-", "");
				customer.setCustomerUniqueCode(replace);
				customer.setJoiningTime(LocalDate.now());
			} else {
				customer.setCustomerUniqueCode(partOfUuid);
				customer.setJoiningTime(LocalDate.now());
			}
			emailSender.sendSimpleEmail(customer.getEmail(),
					"Greetings \nYour Profile in Pigmy Account Has Been Created.\nThank You.",
					"Hello " + customer.getCustomerName());
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("Customer Saved Successfully");
			responseStructure.setData(customerDao.saveCustomer(customer));
			return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
		}
	}

	public ResponseEntity<ResponseStructure<CustomerAccount>> saveAccount(AddAccount request) {
		ResponseStructure<CustomerAccount> responseStructure = new ResponseStructure<>();

		if (customerDao.findCustomerById(request.getCustomerId()) == null) {
			responseStructure.setMessage("Customer Account Doesn't exist " + request.getCustomerId());
			throw new DuplicateDataEntryException("Customer Account Doesn't exist " + request.getCustomerId());
		}
		if (customerAccountNumberRepository.findByAccountNumber(request.getAccountNumber()).isPresent()) {
			throw new DuplicateDataEntryException("Account number already exists " + request.getAccountNumber());
		}
		if (customerAccountNumberRepository
				.findByCustomerIdAndAccountType(request.getCustomerId(), request.getAccountType()).isPresent()) {
			throw new DuplicateDataEntryException(
					"Account Type already exists for Customer " + request.getAccountType());
		}

		else {
			CustomerAccount customerAccount = new CustomerAccount();
			customerAccount.setCustomer(customerDao.findCustomerById(request.getCustomerId()));
			customerAccount.setAccountNumber(request.getAccountNumber());
			customerAccount.setAccountType(request.getAccountType());
			customerAccount.setBalance(0);

			customerAccount = customerAccountNumberRepository.save(customerAccount);

			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("Account added Successfully");
			responseStructure.setData(customerAccount);
			return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
		}

	}

	public ResponseEntity<ResponseStructure<List<Customer>>> getAllCustomers() {
		ResponseStructure<List<Customer>> responseStructure = new ResponseStructure<>();
		List<Customer> list = customerDao.getAllCustomers();
		if (list.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Customers Fetched");
			responseStructure.setData(list);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Customer Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<Customer>> getCustomerById(String id) {
		ResponseStructure<Customer> responseStructure = new ResponseStructure<>();
		Customer customer = customerDao.findCustomerById(id);
		if (customer != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Agent Details By Id");
			responseStructure.setData(customer);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Agent ID " + id + ", NOT FOUND");
		}
	}

	public ResponseEntity<ResponseStructure<List<Customer>>> searchQuery(String query) {

		ResponseStructure<List<Customer>> responseStructure = new ResponseStructure<>();
		List<Customer> customerList = new ArrayList<>();
		if (query.matches(".*\\d.*")) {
			Customer customer = customerDao.findCustomerById(query);
			if (customer == null) {
				throw new IdNotFoundException("Customer id " + query + " not found");
			}
			customerList.add(customer);
		} else {
			customerList = customerDao.fetchCustomersByQuery(query);
		}
		if (customerList.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Customers Details By query");
			responseStructure.setData(customerList);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("No match for " + query + ", NOT FOUND");
		}
	}

	public ResponseEntity<ResponseStructure<List<Customer>>> findAgendIdAndCustomerName(String agentId, String query) {
		ResponseStructure<List<Customer>> responseStructure = new ResponseStructure<>();
		List<Customer> customerList = new ArrayList<>();
		if (query.matches(".*\\d.*")) {
			Customer customer = customerDao.findByCustomerIdAndAgentId(query, agentId);
			customerList.add(customer);
		} else {
			customerList = customerDao.findByAgentIdAndCustomerName(agentId, query);
		}
		if (customerList.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Customers Details By query");
			responseStructure.setData(customerList);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("No match for " + query + ", NOT FOUND");
		}
	}
}
