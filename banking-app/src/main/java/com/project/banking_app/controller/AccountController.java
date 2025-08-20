package com.project.banking_app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.banking_app.dto.AccountDto;
import com.project.banking_app.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    //Add account REST API
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    //Get account REST API
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDto> getAccountByAccountNumber(@PathVariable Long accountNumber){
        AccountDto accountDto = accountService.getAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(accountDto);
    }

    //Deposit amount REST API
    @PutMapping("/{accountNumber}/deposit")
    public ResponseEntity<AccountDto> depositAmount(@PathVariable Long accountNumber, @RequestBody Map<String, Double> request){
        double amount = request.get("amount");
        AccountDto accountDto = accountService.depositAmount(accountNumber, amount);
        return ResponseEntity.ok(accountDto);
    }

    //Withdraw amount REST API
    @PutMapping("/{accountNumber}/withdraw")
    public ResponseEntity<AccountDto> withdrawAmount(@PathVariable Long accountNumber, @RequestBody Map<String, Double> request){
        double amount = request.get("amount");
        AccountDto accountDto = accountService.withdrawAmount(accountNumber, amount);
        return ResponseEntity.ok(accountDto);
    }
}
