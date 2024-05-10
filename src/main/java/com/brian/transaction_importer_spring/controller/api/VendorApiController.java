package com.brian.transaction_importer_spring.controller.api;

import com.brian.transaction_importer_spring.dto.VendorJson;
import com.brian.transaction_importer_spring.entity.Vendor;
import com.brian.transaction_importer_spring.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VendorApiController {
    @Autowired
    private VendorRepository vendorRepository;

    @GetMapping("/vendor/{id}")
    public VendorJson findTransactionJsonById(@PathVariable Long id) {
        Vendor vendor = vendorRepository.findById(id).get();
        if (vendor == null) {
            return null;
        }

        return new VendorJson(vendor);
    }
}
