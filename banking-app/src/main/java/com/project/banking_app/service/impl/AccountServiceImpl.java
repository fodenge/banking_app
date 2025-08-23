package com.project.banking_app.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.banking_app.dto.AccountDto;
import com.project.banking_app.dto.CreateAccountDto;
import com.project.banking_app.entity.Account;
import com.project.banking_app.entity.Transaction;
import com.project.banking_app.exception.AccountNotFoundException;
import com.project.banking_app.exception.InsufficientBalanceException;
import com.project.banking_app.exception.InvalidTransactionException;
import com.project.banking_app.mapper.AccountMapper;
import com.project.banking_app.repository.AccountRepository;
import com.project.banking_app.repository.TransactionRepository;
import com.project.banking_app.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public AccountDto createAccount(CreateAccountDto createAccountDto) {
        Account account = AccountMapper.mapToAccount(createAccountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountByAccountNumber(Long accountNumber) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account doesn't exist."));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto depositAmount(Long accountNumber, double amount) {
        if (amount <= 0) throw new InvalidTransactionException("Deposit amount must be greater than 0");

        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account doesn't exist."));

        account.setBalance(account.getBalance() + amount);
        Account savedAccount = accountRepository.save(account);
        transactionRepository.save(new Transaction(null, accountNumber, "DEPOSIT", null, amount, LocalDateTime.now()));
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdrawAmount(Long accountNumber, double amount) {
        if (amount <= 0) throw new InvalidTransactionException("Withdraw amount must be greater than 0");

        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account doesn't exist."));

        if (amount > account.getBalance())
            throw new InsufficientBalanceException("Insufficient balance");

        account.setBalance(account.getBalance() - amount);
        Account savedAccount = accountRepository.save(account);
        transactionRepository.save(new Transaction(null, accountNumber, "WITHDRAW", null, amount, LocalDateTime.now()));
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(AccountMapper::mapToAccountDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto login(Long accountNumber, String password) {
        Account account = accountRepository.findByAccountNumberAndPassword(accountNumber, password)
                .orElseThrow(() -> new AccountNotFoundException("Invalid account number or password"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
        public void transferMoney(Long senderAccNo, Long receiverAccNo, double amount) {
            if (amount <= 0) throw new InvalidTransactionException("Transfer amount must be greater than 0");
            if (senderAccNo.equals(receiverAccNo))
                throw new InvalidTransactionException("Cannot transfer to the same account");

            Account sender = accountRepository.findById(senderAccNo)
                    .orElseThrow(() -> new AccountNotFoundException("Sender account not found"));
            Account receiver = accountRepository.findById(receiverAccNo)
                    .orElseThrow(() -> new AccountNotFoundException("Receiver account not found"));

            if (amount > sender.getBalance())
                throw new InsufficientBalanceException("Insufficient balance");

            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);

            accountRepository.save(sender);
            accountRepository.save(receiver);

            transactionRepository.save(new Transaction(null, senderAccNo, "TRANSFER", receiverAccNo, amount, LocalDateTime.now()));
            transactionRepository.save(new Transaction(null, receiverAccNo, "RECEIVED", senderAccNo, amount, LocalDateTime.now()));
        }

    @Override
    public List<Transaction> getTransactionHistory(Long accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }
}
