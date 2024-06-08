package com.brian.transaction_importer_spring.controller;

import com.brian.transaction_importer_spring.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class VendorController {

    private final VendorRepository vendorRepository;

    @GetMapping("/vendors")
    public String vendors(Model model) {
        model.addAttribute("vendors", vendorRepository.findAll());
        return "vendors";
    }
}
