package com.lab6.frames.dialogs;

import com.lab6.Main;
import com.lab6.Product;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SelectProductDialog {

    public static class SelectProductDialogResult {
        public final DialogResult result;
        public final Product product;

        public SelectProductDialogResult(DialogResult result, Product product) {
            this.result = result;
            this.product = product;
        }
    }

    //Вызвать и получить значения диалога выбора макс. и мин. границ для фильтрации
    public static SelectProductDialogResult show(Component parentComponent, ArrayList<Product> products) {
        JComboBox<Product> availableProductsComboBox = getProductJComboBox(products);

        //Компоненты внутри диалогового окна
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Выберите добавляемый товар:"),
                availableProductsComboBox
        };

        //Показать диалоговое окно
        int selectedOption = JOptionPane.showConfirmDialog(parentComponent, inputs, Main.superShop.getName(), JOptionPane.OK_CANCEL_OPTION);

        //Если нажали OK
        if (selectedOption == JOptionPane.OK_OPTION) {
            Product selectedProduct = (Product) availableProductsComboBox.getSelectedItem();

            if (selectedProduct == null) {
                return new SelectProductDialogResult(DialogResult.INCORRECT_INPUT, null);
            }

            return new SelectProductDialogResult(DialogResult.OK, selectedProduct);
        }

        return new SelectProductDialogResult(DialogResult.CANCEL, null);
    }

    private static JComboBox<Product> getProductJComboBox(ArrayList<Product> allMissingProducts) {
        JComboBox<Product> availableProductsComboBox = new JComboBox<>(allMissingProducts.toArray(new Product[0]));

        //Класс для отображения товаров внутри ComboBox
        availableProductsComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {
                if (value instanceof Product) {
                    Product product = (Product) value;
                    value = String.format("%s | %.2f$ | %.2f",
                            product.getName(), product.getCost(), product.getRating());
                }
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                return this;
            }
        });
        return availableProductsComboBox;
    }
}
