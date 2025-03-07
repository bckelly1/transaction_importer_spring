package com.brian.transaction_importer_spring.dto;

import com.brian.transaction_importer_spring.entity.Transaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Only include non-null fields in the JSON
public class TransactionDTO {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp date;
    private String description;
    private String originalDescription;
    private Double amount;
    private String transactionType;

    private long categoryId;
    private String merchant;
    private String accountName;
    private String mailMessageId;
    private String notes;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.accountName = transaction.getAccount().getName();
        this.categoryId = transaction.getCategory().getId();
        this.date = transaction.getDate();
        this.merchant = transaction.getMerchant().getName();
        this.description = transaction.getDescription();
        this.originalDescription = transaction.getOriginalDescription();
        this.transactionType = transaction.getTransactionType();
        this.mailMessageId = transaction.getMailMessageId();
        this.notes = transaction.getNotes();
    }
}
