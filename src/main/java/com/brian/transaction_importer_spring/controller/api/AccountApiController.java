package com.brian.transaction_importer_spring.controller.api;

import com.brian.transaction_importer_spring.dto.AccountJson;
import com.brian.transaction_importer_spring.entity.Account;
import com.brian.transaction_importer_spring.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountApiController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/account/{id}")
    public AccountJson findAccountJsonById(@PathVariable Long id) {
        Account account = accountRepository.findById(id).get();
        if (account == null) {
            return null;
        }

        return new AccountJson(account);
    }
}
