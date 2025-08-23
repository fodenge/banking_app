// package com.project.banking_app.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;
// import jakarta.servlet.http.HttpSession;

// import com.project.banking_app.dto.AccountDto;
// import com.project.banking_app.service.AccountService;

// @Controller
// public class DashboardController {

//     @Autowired
//     private AccountService accountService;

//     @GetMapping("/dashboard")
// public String showDashboard(HttpSession session, Model model) {
//     AccountDto accountDto = (AccountDto) session.getAttribute("account");
//     if (accountDto == null) {
//         return "redirect:/login";
//     }
//     model.addAttribute("account", accountDto);
//     return "dashboard";
// }

//     @PostMapping("/deposit")
//     public String deposit(HttpSession session, @RequestParam double amount) {
//         Long accountNumber = (Long) session.getAttribute("accountNumber");
//         accountService.depositAmount(accountNumber, amount);
//         return "redirect:/dashboard";
//     }

//     @PostMapping("/withdraw")
//     public String withdraw(HttpSession session, @RequestParam double amount) {
//         Long accountNumber = (Long) session.getAttribute("accountNumber");
//         accountService.withdrawAmount(accountNumber, amount);
//         return "redirect:/dashboard";
//     }

//     @PostMapping("/transfer")
//     public String transfer(HttpSession session,
//                            @RequestParam Long receiverAccNo,
//                            @RequestParam double amount,
//                            Model model) {
//         Long senderAccNo = (Long) session.getAttribute("accountNumber");
//         try {
//             accountService.transferMoney(senderAccNo, receiverAccNo, amount);
//         } catch(RuntimeException e) {
//             model.addAttribute("error", e.getMessage());
//             return "dashboard";
//         }
//         return "redirect:/dashboard";
//     }
// }
