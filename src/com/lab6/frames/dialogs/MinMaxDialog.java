package com.lab6.frames.dialogs;

import com.lab6.Main;
import com.lab6.frames.other.DoubleDialogDocumentFilter;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class MinMaxDialog {
    //Этот класс хранит данные полученные из диалога выбора макс. и мин. границ для фильтрации
    public static class MinMaxDialogResult {
        public final DialogResult result;
        public final int filterIndex;
        public final double minLimit, maxLimit;

        public MinMaxDialogResult(DialogResult result, int filterIndex, double minLimit, double maxLimit) {
            this.result = result;
            this.filterIndex = filterIndex;
            this.minLimit = minLimit;
            this.maxLimit = maxLimit;
        }
    }

    //Вызвать и получить значения диалога выбора макс. и мин. границ для фильтрации
    public static MinMaxDialogResult show(Component parentComponent) {
        //ComboBox для выбора столбца, по которому будем фильтровать
        JComboBox<String> filterTypeComboBox = new JComboBox<>(new String[]{"цене", "рейтингу"});

        //Поле ввода для минимальной граница
        JTextField minLimitTextField = new JTextField();
        //Фильтр, позволяющий принимать только Double
        ((PlainDocument) minLimitTextField.getDocument()).setDocumentFilter(new DoubleDialogDocumentFilter());

        //Поле ввода для максимальной граница
        JTextField maxLimitTextField = new JTextField();
        //Фильтр, позволяющий принимать только Double
        ((PlainDocument) maxLimitTextField.getDocument()).setDocumentFilter(new DoubleDialogDocumentFilter());

        //Компоненты внутри диалогового окна
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Фильтрация по:"),
                filterTypeComboBox,
                new JLabel("Минимальная граница:"),
                minLimitTextField,
                new JLabel("Максимальная граница:"),
                maxLimitTextField
        };

        //Показать диалоговое окно
        int selectedOption = JOptionPane.showConfirmDialog(parentComponent, inputs, Main.superShop.getName(), JOptionPane.OK_CANCEL_OPTION);

        //Если нажали OK
        if (selectedOption == JOptionPane.OK_OPTION) {
            //Если для лимита ничего не введено, то по умолчанию минимальные и максимальные значения Double
            double minLimit = Double.MIN_VALUE;
            if (!minLimitTextField.getText().isEmpty()) {
                minLimit = Double.parseDouble(minLimitTextField.getText());
            }

            double maxLimit = Double.MAX_VALUE;
            if (!maxLimitTextField.getText().isEmpty()) {
                maxLimit = Double.parseDouble(maxLimitTextField.getText());
            }

            //Если минимальный лимит больше максимального, то просто поменять их местами
            if (minLimit > maxLimit) {
                double temp = minLimit;
                minLimit = maxLimit;
                maxLimit = temp;
            }


            return new MinMaxDialogResult(DialogResult.OK, filterTypeComboBox.getSelectedIndex(), minLimit, maxLimit);
        }

        return new MinMaxDialogResult(DialogResult.CANCEL, 0, 0, 0);
    }
}
