package com.brian.transaction_importer_spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "institution")
public class Institution {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;

    @Override
    public String toString() {
        return "{\"Id\": "+id+"," +
                "\"Name\": \""+name+"\"}";
    }
}
