package com.brian.transaction_importer_spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "vendor")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String aliases;

//    @Override
//    public String toString() {
//        return "{\"Id\": "+id+"," +
//                "\"Name\": \""+name+"\"," +
//                "\"Aliases\": \""+aliases+"\"}";
//    }
}
