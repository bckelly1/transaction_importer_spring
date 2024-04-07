package com.brian.transaction_importer_spring.controller;

import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.repository.*;
import com.brian.transaction_importer_spring.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TransactionController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private VendorRepository vendorRepository;


    @GetMapping("/transactions")
    public String showEntities(Model model) {
//        model.addAttribute("accounts", accountRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("transactions", transactionRepository.findTop10ByOrderByIdDesc());
//        model.addAttribute("vendors", vendorRepository.findAllOrderByIdDesc());
        return "transactions";
    }
}
