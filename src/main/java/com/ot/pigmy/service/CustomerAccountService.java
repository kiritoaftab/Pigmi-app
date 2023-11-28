package com.ot.pigmy.service;

import com.ot.pigmy.dao.CustomerAccountDao;
import com.ot.pigmy.dao.CustomerDao;
import com.ot.pigmy.dto.AccountNumberResp;
import com.ot.pigmy.dto.Customer;
import com.ot.pigmy.dto.CustomerAccount;
import com.ot.pigmy.dto.ResponseStructure;
import com.ot.pigmy.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerAccountService {
    @Autowired
    private CustomerAccountDao customerAccountDao;

    @Autowired
    private CustomerDao customerDao;

    public ResponseEntity<ResponseStructure<List<CustomerAccount>>> getAllAccounts(){
        ResponseStructure<List<CustomerAccount>> responseStructure = new ResponseStructure<>();

        List<CustomerAccount> customerAccountList = customerAccountDao.getAllAccounts();

        responseStructure.setStatus(HttpStatus.OK.value());
        responseStructure.setMessage("All Accounts Data");
        responseStructure.setData(customerAccountList);
        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<AccountNumberResp>> fetchDetailsByAccountNumber(String accountNumber){

        ResponseStructure<AccountNumberResp> responseStructure= new ResponseStructure<>();
        AccountNumberResp resp = new AccountNumberResp();

        Optional<CustomerAccount> customerAccountOptional = customerAccountDao.fetchByAccountNumber(accountNumber);
        if(customerAccountOptional.isPresent()){
            CustomerAccount customerAccount = customerAccountOptional.get();

            resp.setAccountNumber(customerAccount.getAccountNumber());
            resp.setAccountType(customerAccount.getAccountType());
            resp.setBalance(customerAccount.getBalance());

            Customer customer = customerAccount.getCustomer();
            resp.setCustomerId(customer.getId());
            resp.setCustomerName(customer.getCustomerName());

        }else{
            throw new IdNotFoundException("No such account found");
        }

        responseStructure.setStatus(HttpStatus.OK.value());
        responseStructure.setMessage("Details by Account Number");
        responseStructure.setData(resp);
        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }
}
