package com.brian.transaction_importer_spring.controller.api;

import com.brian.transaction_importer_spring.dto.TransactionDTO;
import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.repository.TransactionRepository;
import com.brian.transaction_importer_spring.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TransactionApiController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/transaction/")
    public List<Transaction> listTransactions() {
        List<Transaction> transactions = transactionRepository.findTop100ByOrderByIdDesc();
        if (transactions.isEmpty()) {
            return null;
        }

        return transactions;
    }

    @GetMapping("/transaction/{id}")
    public TransactionDTO findTransactionJsonById(@PathVariable final Long id) {
        Transaction transaction = transactionRepository.findById(id).get();
        if (transaction == null) {
            return null;
        }

        return new TransactionDTO(transaction);
    }

    @PostMapping("/transaction/{id}")
    public TransactionDTO updateTransaction(@PathVariable final Long id, @RequestBody final TransactionDTO transactionDTO) {
        Optional<Transaction> updatedTransaction = transactionService.updateTransaction(id, transactionDTO);
        if (updatedTransaction.isPresent()) {
            return new TransactionDTO(updatedTransaction.get());
        } else {
            throw new NullPointerException("Transaction not found with id " + id);
        }
    }
}
