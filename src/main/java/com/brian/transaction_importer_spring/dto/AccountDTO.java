package com.brian.transaction_importer_spring.dto;

import com.brian.transaction_importer_spring.entity.Account;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // Only include non-null fields in the JSON
public class AccountDTO {
    private Long id;
    private String name;

    private String institutionName;
    private Double balance;
    private String alias;
    private String type;
    private Timestamp last_updated; // TODO: wrong field name

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.balance = account.getBalance();
        this.alias  = account.getAlias();
        this.type = account.getType();
        this.institutionName = account.getInstitution().getName();
        this.last_updated = account.getLastUpdated();
    }
}
