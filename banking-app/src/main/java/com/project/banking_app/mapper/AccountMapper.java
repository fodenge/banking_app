package com.project.banking_app.mapper;

import com.project.banking_app.dto.AccountDto;
import com.project.banking_app.dto.CreateAccountDto;
import com.project.banking_app.entity.Account;

public class AccountMapper {
    public static Account mapToAccount(CreateAccountDto createAccountDto){
        Account account = new Account(
            createAccountDto.getAccountNumber(),
            createAccountDto.getAccountHolderName(),
            createAccountDto.getBalance(),
            createAccountDto.getPassword()
        );
        return account;
    }

    public static AccountDto mapToAccountDto(Account account){
        AccountDto accountDto = new AccountDto(
            account.getAccountNumber(),
            account.getAccountHolderName(),
            account.getBalance()
        );
        return accountDto;
    }
}
