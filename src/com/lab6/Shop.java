package com.lab6;

import java.io.Serializable;
import java.util.ArrayList;

public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    //Название магазина
    private final String name;

    //Все доступные товары в магазине
    private final ArrayList<Product> products;

    //Все категории товаров в магазине
    private final ArrayList<Category> categories;

    //Все пользователи магазина
    private final ArrayList<User> users;

    public Shop(String name) {
        this.name = name;

        products = new ArrayList<>();
        categories = new ArrayList<>();
        users = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addProduct(Product product) {
        for (Product currentProduct : products) {
            if (currentProduct.equals(product)) {
                throw new IllegalArgumentException("Такой продукт уже есть.");
            }
        }

        products.add(product);
    }

    public Product getProduct(int index) {
        return products.get(index);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addCategory(Category category) {
        for (Category currentCategory : categories) {
            if (currentCategory.getName().equals(category.getName())) {
                throw new IllegalArgumentException("Такая категория уже существует.");
            }
        }

        categories.add(category);
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void addUser(User user) {
        users.add(user);
    }

    //Найти пользователя с нужным логином и паролем из базы магазина
    public AuthResult getUser(String login, String password) {
        User foundUser = null;

        for (User currentUser : users) {
            if (currentUser.getLogin().equals(login)) {
                foundUser = currentUser;
                break;
            }
        }

        if (foundUser == null)
            return new AuthResult(AuthCode.NO_SUCH_LOGIN, null);

        if (!foundUser.getPassword().equals(password))
            return new AuthResult(AuthCode.WRONG_PASSWORD, foundUser);

        return new AuthResult(AuthCode.SUCCESS, foundUser);
    }
}