package com.ot.pigmy.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
				|| customerDao.findByCustomerPhone(customer.getPhone()) != null
				|| customerDao.findCustomerByAadhaarNumber(customer.getAadhaarNumber()) != null) {
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
			customerAccount.setAccountCode(request.getAccountCode());
			customerAccount.setBalance(request.getBalance());

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

	public ResponseEntity<ResponseStructure<Page<Customer>>> getCustomersWithPaginationAndSorting(int offset,
			int pageSize, String field) {
		ResponseStructure<Page<Customer>> responseStructure = new ResponseStructure<>();
		Page<Customer> page = customerDao.findCustomersWithPaginationAndSorting(offset, pageSize, field);
		if (page.getSize() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Customers Fetched");
			responseStructure.setRecordCount(page.getSize());
			responseStructure.setData(page);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Customers Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<String>> deleteCustomerById(String id) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		Customer customer = customerDao.findCustomerById(id);
		if (customer != null) {
			customerDao.deleteCustomer(customer);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Customer Of Id " + id + " Data Deleted");
			responseStructure.setData("Customer Data Deleted Successfully");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Customer Id " + id + " Not Found");
		}

	}

	public ResponseEntity<ResponseStructure<Customer>> updateCustomer(Customer customer) {
		ResponseStructure<Customer> responseStructure = new ResponseStructure<>();
		Customer admin1 = customerDao.findCustomerById(customer.getId());
		if (admin1 != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Customer Updated Successfully");
			responseStructure.setData(customerDao.saveCustomer(customer));
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Customer Id " + customer.getId() + ", Not Found");
		}
	}

	public ResponseEntity<String> withDrawalOfAmount(String id, String accountType, double withdrawAmount) {
//		Customer customer = customerDao.findCustomerById(id);
//		if (customer != null) {
//			Optional<CustomerAccount> customerAccount = customerAccountNumberRepository
//					.findByCustomerIdAndAccountType(id, accountType);
//			if (customerAccount.isPresent()) {
//				double remBalance = customerAccount.get().getBalance();
//				LocalDate joiningDate = customer.getJoiningTime();
//				LocalDate today = LocalDate.now();
//
//				long daysSinceJoining = ChronoUnit.DAYS.between(joiningDate, today);
//
//				if (daysSinceJoining >= 180) {
//					if (withdrawAmount < remBalance) {
//						if (withdrawAmount == remBalance) {
//							double amount = remBalance - withdrawAmount;
//							CustomerAccount customerAccount2 = new CustomerAccount();
//							customerAccount2.setBalance(amount);
//							customer.setJoiningTime(LocalDate.now());
//							customerAccountNumberRepository.save(customerAccount2);
//
//						} else {
//							return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Amount Entered is Invalid");
//						}
//
//						double amount = remBalance - withdrawAmount;
//						CustomerAccount customerAccount1 = new CustomerAccount();
//						customerAccount1.setBalance(amount);
//						customer.setJoiningTime(LocalDate.now());
//						customerAccountNumberRepository.save(customerAccount1);
//						return ResponseEntity.ok("Customer has been active for at least 180 days.");
//
//					} else {
//						return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Amount Entered is Invalid");
//					}
//				} else if (daysSinceJoining < 180) {
//					if(withdrawAmount<remBalance) {
//						if(withdrawAmount==remBalance) {
//							double amount=remBalance-withdrawAmount;
//							CustomerAccount customerAccount3= new CustomerAccount();
//							customerAccount3.setBalance(amount);
//							customer.setJoiningTime(LocalDate.now());
//							customerAccountNumberRepository.save(customerAccount3);
//						}else {
//							return ResponseEntity.status(HttpStatus.NOT_FOUND).body("InSufficient Balance");
//						}
//						
//						double amount=remBalance-withdrawAmount;
//						CustomerAccount customerAccount3=new CustomerAccount();
//						double percentAmount=amount-(amount*3/100);
//						customerAccount3.setBalance(percentAmount);
//						customer.setJoiningTime(LocalDate.now());
//						customerAccountNumberRepository.save(customerAccount3);
//						return ResponseEntity.ok("Deduction of 3% Because Withdrawing before 6 months."+percentAmount);
//					}
//					
//					
//					return ResponseEntity.ok("Customer is not active for 180 days yet.");
//				} else {
//
//				}
//			} else {
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Account not found");
//			}
//		} else {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
//		}
//		return null;

		Customer customer = customerDao.findCustomerById(id);

		if (customer != null) {
			Optional<CustomerAccount> customerAccountOpt = customerAccountNumberRepository
					.findByCustomerIdAndAccountType(id, accountType);

			if (customerAccountOpt.isPresent()) {
				CustomerAccount customerAccount = customerAccountOpt.get();
				double remBalance = customerAccount.getBalance();
				LocalDate joiningDate = customer.getJoiningTime();
				LocalDate today = LocalDate.now();
				long daysSinceJoining = ChronoUnit.DAYS.between(joiningDate, today);

				if (daysSinceJoining >= 180) {
					if (withdrawAmount <= remBalance) {
						double updatedBalance = remBalance - withdrawAmount;
						customerAccount.setBalance(updatedBalance);
						customer.setJoiningTime(LocalDate.now());
						customerAccountNumberRepository.save(customerAccount);
						return ResponseEntity.ok("Withdrawal successful. Updated balance: " + updatedBalance);
					} else {
						return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Insufficient Balance");
					}
				} else {
					if (withdrawAmount <= remBalance) {

						// Deduct 2% when withdrawing before 6 months
						double percentDeduction = remBalance * 0.02;
						double updatedBalance = remBalance - withdrawAmount - percentDeduction;
//		                    updatedBalance -= percentDeduction;

						customerAccount.setBalance(updatedBalance);
						customer.setJoiningTime(LocalDate.now());
						customerAccountNumberRepository.save(customerAccount);

						return ResponseEntity.ok("Deduction of 2% for withdrawing before 6 months. Updated balance: "
								+ updatedBalance + " Deduction Amount:" + percentDeduction);
					} else {
						return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Insufficient Balance");
					}
				}
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Account not found");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
		}

	}
}