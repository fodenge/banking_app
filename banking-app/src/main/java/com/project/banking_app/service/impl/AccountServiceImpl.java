package com.project.banking_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.banking_app.dto.AccountDto;
import com.project.banking_app.entity.Account;
import com.project.banking_app.mapper.AccountMapper;
import com.project.banking_app.repository.AccountRepository;
import com.project.banking_app.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountByAccountNumber(Long accountNumber) {
        Account account = accountRepository
                            .findById(accountNumber)
                            .orElseThrow(() -> new RuntimeException("Account doesn't exist."));
        return AccountMapper.mapToAccountDto(account);
    }
    
    

    @Override
    public AccountDto depositAmount(Long account_number, double amount) {
        Account account = accountRepository
                            .findById(account_number)
                            .orElseThrow(() -> new RuntimeException("Account doesn't exist."));
        
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

}
