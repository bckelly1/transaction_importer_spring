package com.brian.transaction_importer_spring.repository;

import com.brian.transaction_importer_spring.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    List<Vendor> findAll();

    Vendor findById(long id);
}
