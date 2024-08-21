package com.lab6.frames.tablemodels;

import com.lab6.Main;
import com.lab6.Product;

import java.util.ArrayList;

public class CountedProductTableModel extends  ProductTableModel {
    private final String[] columnNames = {"№", "Продукт", "Цена (₸)", "Рейтинг", "В корзине"};

    public CountedProductTableModel(ArrayList<Product> products) {
        super(products);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {

        Product product = products.get(row);

        switch (col) {
            case 0:
                return table.convertRowIndexToView(row) + 1;
            case 1:
                return product.getName();
            case 2:
                return product.getCost();
            case 3:
                return product.getRating();
            //Количество товаров данного типа в корзине пользователя.
            //Обновлять интерфейс надо вручную!!!
            case 4:
                return Main.activeUser.getBasket().getProducts().stream().filter(prod -> prod == product).count();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int col) {
        switch (col) {
            case 0:
            case 4:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
            case 3:
                return Double.class;
            default:
                return null;
        }
    }
}
