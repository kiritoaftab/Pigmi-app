package com.ot.pigmy.dao;

import com.ot.pigmy.dto.CustomerAccount;
import com.ot.pigmy.dto.Transaction;
import com.ot.pigmy.repository.CustomerAccountNumberRepository;
import com.ot.pigmy.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerAccountDao {

    @Autowired
    private CustomerAccountNumberRepository customerAccountNumberRepository;

    public CustomerAccount saveCustomerAccount(CustomerAccount customerAccount){
        return  customerAccountNumberRepository.save(customerAccount);
    }

    public CustomerAccount addAmount(int amount, String accountNumber){
        Optional<CustomerAccount> customerAccount = customerAccountNumberRepository.findByAccountNumber(accountNumber);
        if(customerAccount.isPresent()){
            CustomerAccount ca = customerAccount.get();
            ca.setBalance(ca.getBalance()+amount);
            ca = customerAccountNumberRepository.save(ca);
            return ca;
        }else{
            return null;
        }
    }
}
