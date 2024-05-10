package com.brian.transaction_importer_spring.controller.api;

import com.brian.transaction_importer_spring.dto.InstitutionJson;
import com.brian.transaction_importer_spring.entity.Institution;
import com.brian.transaction_importer_spring.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstitutionApiController {
    @Autowired
    private InstitutionRepository institutionRepository;

    @GetMapping("/institution/{id}")
    public InstitutionJson findInstitutionJsonById(@PathVariable Long id) {
        Institution institution = institutionRepository.findById(id).get();
        if (institution == null) {
            return null;
        }

        return new InstitutionJson(institution);
    }
}

