package com.brian.transaction_importer_spring.dto;

import com.brian.transaction_importer_spring.entity.Vendor;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // Only include non-null fields in the JSON
public class VendorJson {
    public VendorJson(Vendor vendor) {
        this.id = vendor.getId();
        this.name = vendor.getName();
        this.aliases = vendor.getAliases();
    }

    private Long id;
    private String name;
    private String aliases;

}
