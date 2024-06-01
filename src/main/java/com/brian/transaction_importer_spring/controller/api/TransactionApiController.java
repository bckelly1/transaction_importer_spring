package com.brian.transaction_importer_spring.controller.api;

import com.brian.transaction_importer_spring.dto.TransactionDTO;
import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionApiController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/transaction/")
    public List<Transaction> listTransactions() {
        List<Transaction> transactions = transactionRepository.findTop10ByOrderByIdDesc();
        if (transactions.isEmpty()) {
            return null;
        }

        return transactions;
    }

    @GetMapping("/transaction/{id}")
    public TransactionDTO findTransactionJsonById(@PathVariable Long id) {
        Transaction transaction = transactionRepository.findById(id).get();
        if (transaction == null) {
            return null;
        }

        return new TransactionDTO(transaction);
    }


}
