package com.ot.pigmy.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ot.pigmy.dto.CustomerAccount;
import com.ot.pigmy.repository.CustomerAccountNumberRepository;

@Repository
public class CustomerAccountDao {

    @Autowired
    private CustomerAccountNumberRepository customerAccountNumberRepository;

    public CustomerAccount saveCustomerAccount(CustomerAccount customerAccount){
        return  customerAccountNumberRepository.save(customerAccount);
    }

    public CustomerAccount addAmount(double amount, String accountNumber){
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

    public List<CustomerAccount> getAllAccounts(){
        List<CustomerAccount> customerAccountList = customerAccountNumberRepository.findAll();
        return customerAccountList;
    }
}
