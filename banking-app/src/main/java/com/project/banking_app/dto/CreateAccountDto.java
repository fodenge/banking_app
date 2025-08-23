package com.project.banking_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountDto {
    
    private Long accountNumber;

    private String accountHolderName;

    private double balance;

    private String password;
}
