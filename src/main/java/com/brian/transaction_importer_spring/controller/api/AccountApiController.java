package com.brian.transaction_importer_spring.controller.api;

import com.brian.transaction_importer_spring.dto.AccountDTO;
import com.brian.transaction_importer_spring.entity.Account;
import com.brian.transaction_importer_spring.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountApiController {

    private final AccountRepository accountRepository;

    @GetMapping("/account/{id}")
    public AccountDTO findAccountJsonById(@PathVariable Long id) {
        Account account = accountRepository.findById(id).get();
        if (account == null) {
            return null;
        }

        return new AccountDTO(account);
    }
}
