package com.brian.transaction_importer_spring.controller.api;

import com.brian.transaction_importer_spring.dto.InstitutionDTO;
import com.brian.transaction_importer_spring.entity.Institution;
import com.brian.transaction_importer_spring.repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InstitutionApiController {

    private final InstitutionRepository institutionRepository;

    @GetMapping("/institution/{id}")
    public InstitutionDTO findInstitutionJsonById(@PathVariable Long id) {
        Institution institution = institutionRepository.findById(id).get();
        if (institution == null) {
            return null;
        }

        return new InstitutionDTO(institution);
    }
}

