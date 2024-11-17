package com.brian.transaction_importer_spring.controller.api;

import com.brian.transaction_importer_spring.dto.AccountDTO;
import com.brian.transaction_importer_spring.entity.Account;
import com.brian.transaction_importer_spring.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
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

    @GetMapping("/accounts")
    public List<AccountDTO> findAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDTO> dtos = new ArrayList<>();
        for (Account account : accounts) {
            dtos.add(new AccountDTO(account));
        }
        return dtos;
    }
}
