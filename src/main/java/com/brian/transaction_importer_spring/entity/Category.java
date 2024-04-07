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

@Entity
@Getter
@Setter
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;


    @ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
    private Category parent_category; // TODO: Self referential field


    // reverse relationship
//    @ManyToOne(targetEntity = Transaction.class, fetch = FetchType.LAZY)
//    private Transaction[] transactions;

    @Override
    public String toString() {
        return "{\"Name\": \""+name+"\"," +
                "\"Id\": "+id+"}";
    }
}
