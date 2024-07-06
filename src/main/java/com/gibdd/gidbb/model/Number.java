package com.gibdd.gidbb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Number {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numberPlate;

    public Number() {
    }

    public Number(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public Long getId() {
        return id;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

}
