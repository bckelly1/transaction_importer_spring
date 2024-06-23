package com.brian.transaction_importer_spring.service;

import com.brian.transaction_importer_spring.dto.TransactionDTO;
import com.brian.transaction_importer_spring.entity.Category;
import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.repository.CategoryRepository;
import com.brian.transaction_importer_spring.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final CategoryRepository categoryRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> updateTransaction(Long id, TransactionDTO transactionDTO) {
        Category category = categoryRepository.findByName(transactionDTO.getCategoryName());

        return transactionRepository.findById(id).map(transaction -> {
            transaction.setCategory(category);
            transaction.setAmount(transactionDTO.getAmount());
            transaction.setDescription(transactionDTO.getDescription());
            transaction.setNotes(transactionDTO.getNotes());
            transaction.setTransactionType(transactionDTO.getTransactionType());
            return transactionRepository.save(transaction);
        });
    }
}
