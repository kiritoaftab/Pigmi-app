package com.ot.pigmy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ot.pigmy.dto.CustomerAccount;

public interface CustomerAccountNumberRepository extends JpaRepository<CustomerAccount, Long> {

	Optional<CustomerAccount> findByAccountNumber(String accountNumber);

}