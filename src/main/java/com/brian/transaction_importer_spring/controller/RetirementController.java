package com.brian.transaction_importer_spring.controller;

import com.brian.transaction_importer_spring.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class RetirementController {

    private final AccountRepository accountRepository;

    @GetMapping("/retirement/{accountType}")
    public String calculateRetirement(Model model, @PathVariable(value = "accountType") final String accountType) {
        model.addAttribute("accounts", accountRepository.findAll());
        model.addAttribute("accountType", accountType);
        model.addAttribute("sumBalance", accountRepository.sumAccountTypes(accountType));
        return "retirement";
    }
}
