package com.ot.pigmy.service;

import com.ot.pigmy.dao.CustomerAccountDao;
import com.ot.pigmy.dto.CustomerAccount;
import com.ot.pigmy.dto.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerAccountService {
    @Autowired
    private CustomerAccountDao customerAccountDao;

    public ResponseEntity<ResponseStructure<List<CustomerAccount>>> getAllAccounts(){
        ResponseStructure<List<CustomerAccount>> responseStructure = new ResponseStructure<>();

        List<CustomerAccount> customerAccountList = customerAccountDao.getAllAccounts();

        responseStructure.setStatus(HttpStatus.OK.value());
        responseStructure.setMessage("All Accounts Data");
        responseStructure.setData(customerAccountList);
        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }
}
