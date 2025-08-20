package com.project.banking_app.service;

import com.project.banking_app.dto.AccountDto;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountByAccountNumber(Long accountNumber);

    AccountDto depositAmount(Long account_number, double amount);
}
