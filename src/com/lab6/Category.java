package com.lab6;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final ArrayList<Product> products;

    public Category(String name) {
        this.name = name;

        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        for (Product currentProduct : products) {
            if (currentProduct.equals(product)) {
                throw new IllegalArgumentException("Такой продукт уже есть.");
            }
        }

        products.add(product);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

}