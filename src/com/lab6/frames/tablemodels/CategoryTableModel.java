package com.lab6.frames.tablemodels;

import com.lab6.Category;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

//Модель таблицы для хранения информации о продуктах
public class CategoryTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    protected JTable table = null;
    protected final ArrayList<Category> categories;
    private final String[] columnNames = {"№", "Категория"};

    public CategoryTableModel(ArrayList<Category> categories) {
        this.categories = new ArrayList<>(categories);
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
        return categories.size();
    }

    @Override
    public Object getValueAt(int row, int col) {

        Category category = categories.get(row);

        switch (col) {
            case 0:
                return table.convertRowIndexToView(row) + 1;
            case 1:
                return category.getName();
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
            default:
                return null;
        }
    }

    public void addCategory(Category category) {
        categories.add(category);
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    public Category getCategory(int row) {
        return categories.get(row);
    }
}