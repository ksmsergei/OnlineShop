package com.lab6.frames.dialogs;

import com.lab6.Category;
import com.lab6.Main;

import javax.swing.*;
import java.awt.*;

public class CreateCategoryDialog {

    public static class CreateCategoryDialogResult {
        public final DialogResult result;
        public final Category category;

        public CreateCategoryDialogResult(DialogResult result, Category category) {
            this.result = result;
            this.category = category;
        }
    }

    //Вызвать диалог и получить новый товар
    public static CreateCategoryDialogResult show(Component parentComponent) {
        //Поле ввода название категории
        JTextField nameField = new JTextField();


        //Компоненты внутри диалогового окна
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Введите название категории:"),
                nameField,
        };

        //Показать диалоговое окно
        int selectedOption = JOptionPane.showConfirmDialog(parentComponent, inputs, Main.superShop.getName(), JOptionPane.OK_CANCEL_OPTION);

        //Если нажали OK
        if (selectedOption == JOptionPane.OK_OPTION) {
            //Если поле пустое - вернуть ошибку
            if (nameField.getText().isEmpty() ) {
                return new CreateCategoryDialogResult(DialogResult.INCORRECT_INPUT, null);
            }

            //Создать и вернуть продукт
            return new CreateCategoryDialogResult(DialogResult.OK, new Category(nameField.getText()));
        }

        return new CreateCategoryDialogResult(DialogResult.CANCEL, null);
    }
}
