package com.brian.transaction_importer_spring.controller;

import com.brian.transaction_importer_spring.repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class InstitutionController {

    private final InstitutionRepository institutionRepository;

    @GetMapping("/institutions")
    public String institution(Model model) {
        model.addAttribute("institutions", institutionRepository.findAll());
        return "institutions";
    }
}
