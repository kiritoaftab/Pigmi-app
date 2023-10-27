package com.ot.pigmy.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.ot.pigmy.dto.Customer;
import com.ot.pigmy.repository.CustomerRepository;

@Repository
public class CustomerDao {

	@Autowired
	private CustomerRepository customerRepository;

	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	public Customer findCustomerById(String id) {
		Optional<Customer> customer = customerRepository.findById(id);
		return customer.orElse(null);

	}

	public Page<Customer> findCustomersWithPaginationAndSorting(int offset, int pageSize, String field) {
		Page<Customer> customers = customerRepository
				.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
		return customers;
	}

	public List<Customer> findCustomerByAgentId(String id) {
		Optional<List<Customer>> customers = customerRepository.findByAgentId(id);
		return customers.orElse(null);
	}

	public List<Customer> findByCustomerName(String customerName) {
		Optional<List<Customer>> customers = customerRepository.findByCustomerName(customerName);
		return customers.orElse(null);
	}

	public Customer findByCustomerEmail(String email) {
		Optional<Customer> customer = customerRepository.findByEmail(email);
		return customer.orElse(null);
	}

	public Customer findByCustomerPhone(String phone) {
		Optional<Customer> customer = customerRepository.findByPhone(phone);
		return customer.orElse(null);
	}

}