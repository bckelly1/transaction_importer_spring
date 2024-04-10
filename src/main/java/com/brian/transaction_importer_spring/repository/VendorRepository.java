package com.brian.transaction_importer_spring.repository;

import com.brian.transaction_importer_spring.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    List<Vendor> findAll();

    Vendor findById(long id);

    Optional<Vendor> findByName(String name);

    default Vendor findOrCreate(String merchant) {
        Optional<Vendor> vendor = this.findByName(merchant);
        return vendor.orElseGet(() -> {
            Vendor newVendor = new Vendor();
            newVendor.setName(merchant);
            return this.save(newVendor);
        });
    }
}
