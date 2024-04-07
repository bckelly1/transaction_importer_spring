package com.brian.transaction_importer_spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "transaction", schema = "brian_home") // TODO: maybe change name?
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
    private Category category; // TODO: FK
//    @ManyToOne(targetEntity = Vendor.class, fetch = FetchType.EAGER)
//    private int merchant; // TODO: FK
//    @ManyToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
//    private int account; // TODO: FK
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
