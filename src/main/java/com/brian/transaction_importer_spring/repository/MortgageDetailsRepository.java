package com.brian.transaction_importer_spring.repository;

import com.brian.transaction_importer_spring.entity.MortgageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MortgageDetailsRepository extends JpaRepository<MortgageDetails, Long> {
    MortgageDetails findById(long id);

    MortgageDetails findByAccountId(long accountId);
}
