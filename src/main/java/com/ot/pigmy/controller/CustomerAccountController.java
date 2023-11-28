package com.ot.pigmy.controller;

import com.ot.pigmy.dto.CustomerAccount;
import com.ot.pigmy.dto.ResponseStructure;
import com.ot.pigmy.service.CustomerAccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*")
public class CustomerAccountController {

    @Autowired
    private CustomerAccountService customerAccountService;




    @ApiOperation(value = "Get All Accounts", notes = "returns all accounts")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "All Accounts data"),
            @ApiResponse(code = 409, message = "No accounts ") })
    @GetMapping(value = "/fetchAccounts")
    public ResponseEntity<ResponseStructure<List<CustomerAccount>>> fetchAccounts() {
        return customerAccountService.getAllAccounts();
    }

}
