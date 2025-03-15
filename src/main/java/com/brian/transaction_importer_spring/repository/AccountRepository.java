package com.brian.transaction_importer_spring.repository;

import com.brian.transaction_importer_spring.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByName(String name);

    Account findById(long id);

    Account findByAlias(String alias);

    @Query("select sum(balance) from Account where type = ?1")
    double sumAccountTypes(String accountType);
}
