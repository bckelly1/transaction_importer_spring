package com.brian.transaction_importer_spring.controller;

import com.brian.transaction_importer_spring.entity.Category;
import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.repository.CategoryRepository;
import com.brian.transaction_importer_spring.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class TransactionController {

    private final CategoryRepository categoryRepository;

    private final TransactionRepository transactionRepository;


    @GetMapping("/transaction/{transactionId}/")
    public String getTransaction(@PathVariable(value="transactionId") Long transactionId, Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        Transaction transaction = transactionRepository.findById(transactionId).get();
        model.addAttribute("transaction", transaction);
        return "transaction";
    }

    @GetMapping("/transactions")
    public String showEntities(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("transactions", transactionRepository.findTop20ByOrderByIdDesc());
        return "transactions";
    }

    @GetMapping("/transactions/unknown")
    public String getUnknownTransactions(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());

        Category unknown = categoryRepository.findByName("Unknown");
        model.addAttribute("transactions", transactionRepository.findTransactionByCategory(unknown));
        return "transactions";
    }

    @PostMapping("/transaction/{transactionId}/delete")
    @ResponseBody
    public ResponseEntity<Void> deleteTransaction(@PathVariable(value="transactionId") Long transactionId) {
        System.out.println("Received delete transaction request for " + transactionId);
        transactionRepository.deleteById(transactionId);

        return ResponseEntity.ok().build();

    }
}
