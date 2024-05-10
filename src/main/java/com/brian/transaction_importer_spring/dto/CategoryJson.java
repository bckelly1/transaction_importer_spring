package com.brian.transaction_importer_spring.dto;

import com.brian.transaction_importer_spring.entity.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // Only include non-null fields in the JSON
public class CategoryJson {
    public CategoryJson(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    private Long id;
    private String name;

    @JsonProperty("parent_category")
    private Category parent_category;
}
