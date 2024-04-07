package com.brian.transaction_importer_spring.controller;

import com.brian.transaction_importer_spring.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InstitutionController {
    @Autowired
    private InstitutionRepository institutionRepository;

    @GetMapping("/institutions")
    public String institution(Model model) {
        model.addAttribute("institutions", institutionRepository.findAll());
        return "institutions";
    }
}
