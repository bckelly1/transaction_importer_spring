package com.brian.transaction_importer_spring.controller;

import com.brian.transaction_importer_spring.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;

    @GetMapping("/accounts")
    public String showAccounts(Model model) {
        model.addAttribute("accounts", accountRepository.findAll());
        return "accounts";
    }
}
