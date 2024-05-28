package com.brian.transaction_importer_spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@ToString
@Getter
@Setter
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
    private Timestamp last_updated; // TODO: wrong field name

//    @Override
//    public String toString() {
//        return "{\"Id\": "+id+"," +
//                "\"Name\": \""+name+"\"," +
//                "\"Institution\": \""+institution+"\"," +
//                "\"Balance\": "+balance+"," +
//                "\"alias\": \""+alias+"\"," +
//                "\"alias\": \""+type+"\"," +
//                "\"last_update\": "+last_updated+"}";
//    }
}
