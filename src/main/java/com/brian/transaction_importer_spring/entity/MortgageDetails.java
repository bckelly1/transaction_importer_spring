package com.brian.transaction_importer_spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // TODO: Understand this, JSON serialization issue
@Table(name = "mortgage_terms")
public class MortgageDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origination_date")
    private Timestamp originationDate;

    @OneToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    private Account account; // TODO: Actually a FK

    @Column(name = "term_in_years")
    private int termInYears;

    @Column(name = "interest_rate")
    private double interestRate;

    @Column(name = "loan_amount")
    private double loanAmount;
}
