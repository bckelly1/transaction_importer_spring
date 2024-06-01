package com.brian.transaction_importer_spring.dto;

import com.brian.transaction_importer_spring.entity.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // Only include non-null fields in the JSON
public class CategoryDTO {
    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        if(category.getParent() != null) {
            this.parentCategoryName = category.getParent().getName();
            this.parentCategoryId = category.getParent().getId();
        }
    }

    private Long id;
    private String name;

    private String parentCategoryName;
    private Long parentCategoryId;
}
