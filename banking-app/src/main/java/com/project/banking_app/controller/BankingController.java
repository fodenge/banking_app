package com.project.banking_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.banking_app.dto.AccountDto;
import com.project.banking_app.dto.LoginDto;
import com.project.banking_app.entity.Transaction;
import com.project.banking_app.exception.InsufficientBalanceException;
import com.project.banking_app.service.AccountService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BankingController {

    @Autowired
    private AccountService accountService;

    // Login Page (GET)
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }
    
    // Login Page Processing (POST)
    @PostMapping("/login")
    public String login(@RequestParam Long accountNumber,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        try {
            AccountDto account = accountService.login(accountNumber, password);
            session.setAttribute("accountNumber", accountNumber);
            return "redirect:/dashboard";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "login";
        }
    }

    // Dashboard (GET)
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Long accountNumber = (Long) session.getAttribute("accountNumber");
        if (accountNumber == null) return "redirect:/login";

        try {
            AccountDto account = accountService.getAccountByAccountNumber(accountNumber);
            model.addAttribute("account", account);
            return "dashboard";
        } catch (Exception ex) {
            session.invalidate();
            return "redirect:/login";
        }
    }

    // Deposit Processing (GET)
    @GetMapping("/deposit")
    public String showDepositPage(HttpSession session, Model model) {
        if (session.getAttribute("accountNumber") == null) return "redirect:/login";
        return "deposit";
    }

    // Deposit Processing (POST)
    @PostMapping("/deposit")
    public String deposit(HttpSession session,
                          @RequestParam double amount,
                          Model model) {
        Long accountNumber = (Long) session.getAttribute("accountNumber");
        try {
            accountService.depositAmount(accountNumber, amount);
            return "redirect:/dashboard";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "deposit";
        }
    }

    // Withdraw Page (GET)
    @GetMapping("/withdraw")
    public String showWithdrawPage(HttpSession session, Model model) {
        if (session.getAttribute("accountNumber") == null) {
            return "redirect:/login";
        }
        return "withdraw"; // loads the withdraw.html form
    }

    // Withdraw Processing (POST)
    @PostMapping("/withdraw")
    public String withdraw(HttpSession session,
                        @RequestParam double amount,
                        Model model) {
        Long accountNumber = (Long) session.getAttribute("accountNumber");
        try {
            accountService.withdrawAmount(accountNumber, amount);
            model.addAttribute("message", "Withdrawal successful!");
        } catch (IllegalArgumentException | InsufficientBalanceException e) {
            model.addAttribute("error", e.getMessage());
            return "withdraw"; // stay on withdraw page if error
        }
        return "redirect:/dashboard";
    }

    // Transfer Money (GET)
    @GetMapping("/transfer")
    public String showTransferPage(HttpSession session, Model model) {
        Long accountNumber = (Long) session.getAttribute("accountNumber");
        if (accountNumber == null) return "redirect:/login";

        List<AccountDto> allAccounts = accountService.getAllAccounts();
        allAccounts.removeIf(a -> a.getAccountNumber().equals(accountNumber)); // remove self
        model.addAttribute("accounts", allAccounts);
        return "transfer";
    }

    //Tansfer Money (POST)
    @PostMapping("/transfer")
    public String transferMoney(HttpSession session,
                                @RequestParam Long receiverAccNo,
                                @RequestParam double amount,
                                Model model) {
        Long senderAccNo = (Long) session.getAttribute("accountNumber");
        try {
            accountService.transferMoney(senderAccNo, receiverAccNo, amount);
            return "redirect:/dashboard";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            // reload account list for selection
            List<AccountDto> allAccounts = accountService.getAllAccounts();
            allAccounts.removeIf(a -> a.getAccountNumber().equals(senderAccNo));
            model.addAttribute("accounts", allAccounts);
            return "transfer";
        }
    }

    // Transaction History
    @GetMapping("/transactions")
    public String viewTransactions(HttpSession session, Model model) {
        Long accountNumber = (Long) session.getAttribute("accountNumber");
        if (accountNumber == null) return "redirect:/login";

        try {
            List<Transaction> transactions = accountService.getTransactionHistory(accountNumber);
            model.addAttribute("transactions", transactions);
            return "transactions";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "dashboard";
        }
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
