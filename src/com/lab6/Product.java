package com.lab6;

import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final double cost, rating;

    public Product(String name, double cost, double rating) {
        this.name = name;
        this.cost = cost;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public double getRating() {
        return rating;
    }
}