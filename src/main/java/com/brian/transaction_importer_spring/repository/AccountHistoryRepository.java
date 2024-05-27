package com.brian.transaction_importer_spring.repository;

import com.brian.transaction_importer_spring.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {
}
