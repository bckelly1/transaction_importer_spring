package com.brian.transaction_importer_spring.dto;

import com.brian.transaction_importer_spring.entity.Transaction;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // Only include non-null fields in the JSON
public class TransactionDTO {
    private Long id;
    private Timestamp date;
    private String description;
    private String originalDescription;
    private Double amount;
    private String transactionType;

    private String categoryName;
    private String vendorName;
    private String accountName;
    private String mailMessageId;
    private String notes;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.accountName = transaction.getAccount().getName();
        this.amount = transaction.getAmount();
        this.categoryName = transaction.getCategory().getName();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.originalDescription = transaction.getOriginalDescription();
        this.transactionType = transaction.getTransactionType();
        this.vendorName = transaction.getMerchant().getName();
        this.mailMessageId = transaction.getMailMessageId();
        this.notes = transaction.getNotes();
    }
}
