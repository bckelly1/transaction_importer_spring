package com.brian.transaction_importer_spring.controller;

import com.brian.transaction_importer_spring.repository.AccountRepository;
import com.brian.transaction_importer_spring.repository.MortgageDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MortgageController {

    private final AccountRepository accountRepository;

    private final MortgageDetailsRepository mortgageDetailsRepository;

    @GetMapping("/mortgage/{account_id}")
    public String mortgageDetails(@PathVariable(value = "account_id") final Long accountId, Model model) {
        System.out.println("Received mortgage details request for " + accountId);

        model.addAttribute("account", accountRepository.findById(accountId).get());
        model.addAttribute("mortgageDetails", mortgageDetailsRepository.findByAccountId(accountId));

        return "mortgageDetails";

    }
}
