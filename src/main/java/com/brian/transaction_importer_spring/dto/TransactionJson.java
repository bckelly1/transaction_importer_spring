package com.brian.transaction_importer_spring.dto;

import com.brian.transaction_importer_spring.entity.Account;
import com.brian.transaction_importer_spring.entity.Category;
import com.brian.transaction_importer_spring.entity.Transaction;
import com.brian.transaction_importer_spring.entity.Vendor;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // Only include non-null fields in the JSON
public class TransactionJson {
    public TransactionJson(Transaction transaction) {
        this.id = transaction.getId();
        this.account = transaction.getAccount();
        this.amount = transaction.getAmount();
        this.category = transaction.getCategory();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.originalDescription = transaction.getOriginalDescription();
        this.transactionType = transaction.getTransactionType();
        this.merchant = transaction.getMerchant();
        this.mailMessageId = transaction.getMailMessageId();
        this.notes = transaction.getNotes();
    }

    private Long id;
    private Timestamp date;
    private String description;
    private String originalDescription;
    private Double amount;
    private String transactionType;

    private Category category; // TODO: FK

    private Vendor merchant; // TODO: FK

    private Account account; // TODO: FK
    private String mailMessageId;
    private String notes;

}