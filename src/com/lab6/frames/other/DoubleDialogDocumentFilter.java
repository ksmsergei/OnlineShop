package com.lab6.frames.other;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class DoubleDialogDocumentFilter extends DocumentFilter {

    //Проверить, является ли ведённый текст подходящим Double (для ввода в диалоговом окне)
    protected boolean isOkay(String text) {
        //Можно оставлять пустое поле
        if (text.isEmpty())
            return true;

        //По умолчанию формат ".*" поддерживается. Запретить это и использовать формат "0.*"
        if (text.charAt(0) == '.')
            return false;

        //В начале по умолчанию можно ставить знак - мы это запретим
        if (text.contains("-") || text.contains("+"))
            return false;

        try {
            //Попытаться преобразовать строку в Double
            //Если поймаем исключение, то введено некорректное число
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string,
                             AttributeSet attr) throws BadLocationException {

        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.insert(offset, string);

        if (isOkay(sb.toString())) {
            super.insertString(fb, offset, string, attr);
        } else {
            java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
                        AttributeSet attrs) throws BadLocationException {

        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.replace(offset, offset + length, text);

        if (isOkay(sb.toString())) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            java.awt.Toolkit.getDefaultToolkit().beep();
        }

    }

    @Override
    public void remove(FilterBypass fb, int offset, int length)
            throws BadLocationException {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.delete(offset, offset + length);

        if (isOkay(sb.toString())) {
            super.remove(fb, offset, length);
        } else {
            java.awt.Toolkit.getDefaultToolkit().beep();
        }

    }
}