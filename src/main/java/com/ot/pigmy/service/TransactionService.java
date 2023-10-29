package com.ot.pigmy.service;

import com.ot.pigmy.dao.CustomerAccountDao;
import com.ot.pigmy.dao.CustomerDao;
import com.ot.pigmy.dao.TransactionDao;
import com.ot.pigmy.dto.Customer;
import com.ot.pigmy.dto.CustomerAccount;
import com.ot.pigmy.dto.ResponseStructure;
import com.ot.pigmy.dto.Transaction;
import com.ot.pigmy.dto.request.TransactionResp;
import com.ot.pigmy.exception.DuplicateDataEntryException;
import com.ot.pigmy.exception.IdNotFoundException;
import com.ot.pigmy.repository.CustomerAccountNumberRepository;
import com.ot.pigmy.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TransactionService {
    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerAccountDao customerAccountDao;

    @Autowired
    private CustomerAccountNumberRepository customerAccountNumberRepository;
    @Autowired
    private EmailSender emailSender;

    public ResponseEntity<ResponseStructure<TransactionResp>> saveTransaction(Transaction transaction) {

        ResponseStructure<TransactionResp> responseStructure = new ResponseStructure<>();

        if(!customerAccountNumberRepository.findByAccountNumber(transaction.getAccountNumber()).isPresent()){
            throw new IdNotFoundException("Account number "+ transaction.getAccountNumber()+" does not exist");
        }
        if(customerDao.findByCustomerIdAndAgentId(transaction.getCustomerId(), transaction.getAgentId()) == null){
            throw new IdNotFoundException("Customer Id "+transaction.getCustomerId()+" does not belong to "+transaction.getAgentId());
        }

        if(!transactionDao.verifyTransaction(transaction.getCustomerId(),transaction.getAgentId(), LocalDate.now())){
            throw new DuplicateDataEntryException("Transaction already done for this Customer "
                    +transaction.getCustomerId()
                    +", for today "+LocalDate.now().toString());
        }

        CustomerAccount customerAccount = customerAccountDao.addAmount(transaction.getAmount(),transaction.getAccountNumber());
        transaction = transactionDao.saveTransaction(transaction);

        TransactionResp transactionResp = new TransactionResp();
        transactionResp.setTransaction(transaction);
        transactionResp.setCustomer(customerAccount.getCustomer());

        emailSender.sendSimpleEmail(
                customerAccount.getCustomer().getEmail(),
                "Hello " + customerAccount.getCustomer().getCustomerName()
                        + ", amount of Rs. "+transaction.getAmount() +" has been transferred to your account ",
                "Transaction Completed");
        responseStructure.setStatus(HttpStatus.CREATED.value());
        responseStructure.setMessage("Transaction Saved Successfully");
        responseStructure.setData(transactionResp);
        return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);

    }
}
