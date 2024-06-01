package com.brian.transaction_importer_spring.controller.api;

import com.brian.transaction_importer_spring.dto.CategoryDTO;
import com.brian.transaction_importer_spring.entity.Category;
import com.brian.transaction_importer_spring.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryApiController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/category/{id}")
    public CategoryDTO findAccountJsonById(@PathVariable Long id) {
        Category category = categoryRepository.findById(id).get();
        if (categoryRepository == null) {
            return null;
        }

        return new CategoryDTO(category);
    }
}
