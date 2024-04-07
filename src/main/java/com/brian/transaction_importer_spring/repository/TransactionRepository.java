package com.brian.transaction_importer_spring.repository;

import com.brian.transaction_importer_spring.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAll();

    List<Transaction> findTop10ByOrderByIdDesc();

    Transaction findById(long id);
}
