package com.lab6.frames.dialogs;

import com.lab6.Main;
import com.lab6.frames.other.DoubleDialogDocumentFilter;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class MoneyInputDialog {

    public static class MoneyInputDialogResult {
        public final DialogResult result;
        public final double moneyAmount;

        public MoneyInputDialogResult(DialogResult result, double moneyAmount) {
            this.result = result;
            this.moneyAmount = moneyAmount;
        }
    }

    //Вызвать и получить значения диалога внесения суммы
    public static MoneyInputDialogResult show(Component parentComponent) {

        //Поле ввода для внесённой суммы
        JTextField moneyAmountField = new JTextField();
        //Фильтр, позволяющий принимать только Double
        ((PlainDocument) moneyAmountField.getDocument()).setDocumentFilter(new DoubleDialogDocumentFilter());

        //Компоненты внутри диалогового окна
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Введите вносимую сумму:"),
                moneyAmountField
        };

        //Показать диалоговое окно
        int selectedOption = JOptionPane.showConfirmDialog(parentComponent, inputs, Main.superShop.getName(), JOptionPane.OK_CANCEL_OPTION);

        //Если нажали OK
        if (selectedOption == JOptionPane.OK_OPTION) {
            //Если поле пустое, то выдать код некорректного ввода
            if (moneyAmountField.getText().isEmpty()) {
                return new MoneyInputDialogResult(DialogResult.INCORRECT_INPUT, 0);
            }

            //Всё ок, вернуть ведённую сумму
            return new MoneyInputDialogResult(DialogResult.OK, Double.parseDouble(moneyAmountField.getText()));
        }

        return new MoneyInputDialogResult(DialogResult.CANCEL, 0);
    }
}
