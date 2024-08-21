package com.lab6.frames.dialogs;

import com.lab6.Main;
import com.lab6.Product;
import com.lab6.frames.other.DoubleDialogDocumentFilter;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class CreateProductDialog {

    public static class CreateProductDialogResult {
        public final DialogResult result;
        public final Product product;

        public CreateProductDialogResult (DialogResult result, Product product) {
            this.result = result;
            this.product = product;
        }
    }

    //Вызвать диалог и получить новый товар
    public static CreateProductDialogResult show(Component parentComponent) {
        //Поле ввода название продукта
        JTextField nameField = new JTextField();

        //Поле ввода цены
        JTextField costField = new JTextField();
        //Фильтр, позволяющий принимать только Double
        ((PlainDocument) costField.getDocument()).setDocumentFilter(new DoubleDialogDocumentFilter());

        //Поле ввода рейтинга
        JTextField ratingField = new JTextField();
        //Фильтр, позволяющий принимать только Double
        ((PlainDocument) ratingField.getDocument()).setDocumentFilter(new DoubleDialogDocumentFilter());

        //Компоненты внутри диалогового окна
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Введите название продукта:"),
                nameField,
                new JLabel("Введите цену продукта:"),
                costField,
                new JLabel("Введите рейтинг продукта:"),
                ratingField
        };

        //Показать диалоговое окно
        int selectedOption = JOptionPane.showConfirmDialog(parentComponent, inputs, Main.superShop.getName(), JOptionPane.OK_CANCEL_OPTION);

        //Если нажали OK
        if (selectedOption == JOptionPane.OK_OPTION) {
            //Если хоть одно поле пустое - вернуть ошибку
            if (nameField.getText().isEmpty() || costField.getText().isEmpty() || ratingField.getText().isEmpty()) {
                return new CreateProductDialogResult(DialogResult.INCORRECT_INPUT, null);
            }

            //Создать и вернуть продукт
            Product product = new Product(nameField.getText(),
                    Double.parseDouble(costField.getText()),
                    Double.parseDouble(ratingField.getText()));

            return new CreateProductDialogResult(DialogResult.OK, product);
        }

        return new CreateProductDialogResult(DialogResult.CANCEL, null);
    }
}
