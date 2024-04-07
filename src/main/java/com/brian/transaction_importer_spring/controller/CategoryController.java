package com.brian.transaction_importer_spring.controller;

import com.brian.transaction_importer_spring.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories";
    }

    @GetMapping("/categories/populateDropdown")
    public String populateDropdown(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categoriesDropdownList";
    }
}
