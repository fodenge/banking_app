package com.project.banking_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {
    private Long accountNumber;
    private String accountHolderName;
    private double balance;
}
