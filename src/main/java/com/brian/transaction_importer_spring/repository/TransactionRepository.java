package com.brian.transaction_importer_spring.repository;

import com.brian.transaction_importer_spring.entity.Category;
import com.brian.transaction_importer_spring.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAll();

    List<Transaction> findTop10ByOrderByIdDesc();

    Transaction findById(long id);

    @Query("FROM Transaction t where t.category = :category")
    List<Transaction> findTransactionByCategory(@Param("category") Category category);
}
