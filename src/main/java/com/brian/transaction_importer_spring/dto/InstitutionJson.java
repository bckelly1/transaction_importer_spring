package com.brian.transaction_importer_spring.dto;

import com.brian.transaction_importer_spring.entity.Institution;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // Only include non-null fields in the JSON
public class InstitutionJson {
    public InstitutionJson(Institution institution) {
        this.id = institution.getId();
        this.name = institution.getName();
    }

    private Long id;
    private String name;
}
