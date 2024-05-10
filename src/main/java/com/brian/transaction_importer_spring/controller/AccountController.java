package com.brian.transaction_importer_spring.controller;

import com.brian.transaction_importer_spring.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts/")
    public String showAccounts(Model model) {
        model.addAttribute("accounts", accountRepository.findAll());
        return "accounts";
    }
}
