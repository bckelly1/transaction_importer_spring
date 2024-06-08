package com.brian.transaction_importer_spring.controller.api;

import com.brian.transaction_importer_spring.dto.VendorDTO;
import com.brian.transaction_importer_spring.entity.Vendor;
import com.brian.transaction_importer_spring.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VendorApiController {

    private final VendorRepository vendorRepository;

    @GetMapping("/vendor/{id}")
    public VendorDTO findTransactionJsonById(@PathVariable Long id) {
        Vendor vendor = vendorRepository.findById(id).get();
        if (vendor == null) {
            return null;
        }

        return new VendorDTO(vendor);
    }
}
