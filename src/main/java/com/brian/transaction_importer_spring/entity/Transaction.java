package com.brian.transaction_importer_spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Timestamp date;
    private String description;
    private String originalDescription;
    private Double amount;
    private String transactionType;

    @ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; // TODO: FK

    @ManyToOne(targetEntity = Vendor.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor merchant; // TODO: FK

    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account; // TODO: FK
    private String mailMessageId;
    private String notes;

    @Override
    public String toString() {
        return "{\"Id\": "+id+"," +
                "\"Description\": \""+description+"\"," +
                "\"Original Description\": \""+originalDescription+"\"," +
                "\"Amount\": "+amount+"," +
                "\"Transaction Type\": \""+transactionType+"\"}";
    }
}
