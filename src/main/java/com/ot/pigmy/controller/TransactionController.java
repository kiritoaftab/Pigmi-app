package com.ot.pigmy.controller;

import com.ot.pigmy.dto.Customer;
import com.ot.pigmy.dto.ResponseStructure;
import com.ot.pigmy.dto.Transaction;
import com.ot.pigmy.dto.request.TransactionResp;
import com.ot.pigmy.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "*")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @ApiOperation(value = "Save Transaction", notes = "Input is Transaction Object and return TransactionResp object")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 409, message = "Transaction Already Exist") })
    @PostMapping(value = "/save", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ResponseStructure<TransactionResp>> saveCustomer(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }
}
