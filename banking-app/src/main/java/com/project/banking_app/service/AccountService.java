package com.project.banking_app.service;

import java.util.List;

import com.project.banking_app.dto.AccountDto;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountByAccountNumber(Long accountNumber);

    AccountDto depositAmount(Long accountNumber, double amount);

    AccountDto withdrawAmount(Long accountNumber, double amount);

    List<AccountDto> getAllAccounts();
}
