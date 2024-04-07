package com.brian.transaction_importer_spring.controller;

import com.brian.transaction_importer_spring.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VendorController {
    @Autowired
    private VendorRepository vendorRepository;

    @GetMapping("/vendors")
    public String vendors(Model model) {
        model.addAttribute("vendors", vendorRepository.findAll());
        return "vendors";
    }
}
