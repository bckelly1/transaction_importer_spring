package com.brian.transaction_importer_spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // TODO: Understand this, JSON serialization issue
@Table(name = "mortgage_terms")
public class MortgageTerms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    private Account account; // TODO: Actually a FK
    private Timestamp originationDate;
    private Long termInYears;

}
