package com.project.banking_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.banking_app.service.AccountService;

@Controller
public class WebController {
    
    @Autowired
    private AccountService accountService;

     @GetMapping("/accounts-page")  
    public String showAccounts(Model model) {
        model.addAttribute("accounts", accountService.getAllAccounts());
        return "accounts"; 
    }
}
