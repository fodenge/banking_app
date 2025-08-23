package com.project.banking_app.service;

import java.util.List;

import com.project.banking_app.dto.AccountDto;
import com.project.banking_app.dto.CreateAccountDto;
import com.project.banking_app.entity.Transaction;

public interface AccountService {

    AccountDto createAccount(CreateAccountDto createAccountDto);

    AccountDto getAccountByAccountNumber(Long accountNumber);

    AccountDto depositAmount(Long accountNumber, double amount);

    AccountDto withdrawAmount(Long accountNumber, double amount);

    List<AccountDto> getAllAccounts();

    AccountDto login(Long accountNumber, String password);

    void transferMoney(Long senderAccNo, Long receiverAccNo, double amount);

    List<Transaction> getTransactionHistory(Long accountNumber);

}
