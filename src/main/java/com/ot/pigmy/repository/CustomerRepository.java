package com.ot.pigmy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ot.pigmy.dto.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

	public Optional<Customer> findByEmail(String email);

	public Optional<Customer> findByPhone(String phone);

	public Optional<List<Customer>> findByCustomerName(String name);

}