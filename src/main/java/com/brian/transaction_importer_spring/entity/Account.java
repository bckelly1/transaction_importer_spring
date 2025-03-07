package com.brian.transaction_importer_spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // TODO: Understand this, JSON serialization issue
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @ManyToOne(targetEntity = Institution.class, fetch = FetchType.LAZY)
    private Institution institution; // TODO: Actually a FK
    private Double balance;
    private String alias;
    private String type;

    @Column(name = "last_updated")
    private Timestamp lastUpdated; // TODO: wrong field name

}
