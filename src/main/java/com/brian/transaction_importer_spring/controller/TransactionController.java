package com.brian.transaction_importer_spring.controller;

import com.brian.transaction_importer_spring.repository.CategoryRepository;
import com.brian.transaction_importer_spring.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TransactionController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    @GetMapping("/transactions")
    public String showEntities(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("transactions", transactionRepository.findTop10ByOrderByIdDesc());
        return "transactions";
    }
}
