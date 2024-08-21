package com.lab6.frames.tablemodels;

import com.lab6.Product;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

//Модель таблицы для хранения информации о продуктах
public class ProductTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    protected JTable table = null;
    protected final ArrayList<Product> products;
    private final String[] columnNames = {"№", "Продукт", "Цена (₸)", "Рейтинг"};

    public ProductTableModel(ArrayList<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public void setTable(JTable table) {
        this.table = table;
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
    public int getRowCount() {
        return products.size();
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
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int col) {
        switch (col) {
            case 0:
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

    public void addProduct(Product product) {
        products.add(product);
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    public Product getProduct(int row) {
        return products.get(row);
    }

    public void removeProduct(int row) {
        products.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void refreshCell(int row, int col) {
        fireTableCellUpdated(row, col);
    }
}