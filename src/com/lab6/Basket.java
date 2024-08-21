package com.lab6;

import java.io.Serializable;
import java.util.ArrayList;

public class Basket implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ArrayList<Product> products;

    public Basket() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void clear() {
        products.clear();
    }

    //Полная стоимость товаров в корзине
    public double getTotalCost() {
        double totalCost = 0;
        for (Product currentProduct : products) {
            totalCost += currentProduct.getCost();
        }
        return totalCost;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}