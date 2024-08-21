/*
 * Created by JFormDesigner on Thu Mar 21 01:22:25 QYZT 2024
 */

package com.lab6.frames;

import java.awt.event.*;
import com.lab6.*;
import com.lab6.frames.dialogs.MoneyInputDialog;
import com.lab6.frames.tablemodels.ProductTableModel;
import static com.lab6.Main.showMessage;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Сергей
 */
public class ShowBasketFrame extends JFrame {
    public ShowBasketFrame() {
        initComponents();
    }

    //Обновить лейбл с общей стоимостью корзины
    private void updateTotalCost() {
        lblTotalCost.setText(String.format("<html>Итого: <b>%.2f₸<b><html>", Main.activeUser.getBasket().getTotalCost()));
    }

    private void createUIComponents() {
        tbProducts = Main.createTable();
    }

    //Заполнение таблицы нужными данными
    private void thisComponentShown(ComponentEvent e) {
        //Очистить прошлые данные и заполнить таблицу
        ProductTableModel model = new ProductTableModel(Main.activeUser.getBasket().getProducts());
        model.setTable(tbProducts);
        tbProducts.setModel(model);

        Main.prepareTable(tbProducts, 25, this.getWidth() - 25 - 65 - 65, 65, 65);

        updateTotalCost();
    }

    //Оформить заказ
    private void btnCheckout(ActionEvent e) {
        if (Main.activeUser.getBasket().isEmpty()) {
            Main.showMessage(this, "Ваша корзина пуста.");
            return;
        }

        //Внесение суммы
        MoneyInputDialog.MoneyInputDialogResult dialogResult = MoneyInputDialog.show(this);

        switch (dialogResult.result) {
            case CANCEL:
                return;

            case INCORRECT_INPUT:
                Main.showMessage(this, "Введена некорректная сумма.");
                return;
        }

        double moneyAmount = dialogResult.moneyAmount;

        //Внесено меньше, чем общая стоимость корзины
        if (moneyAmount < Main.activeUser.getBasket().getTotalCost()) {
            Main.showMessage(this, "Недостаточно средств для покупки.");
            return;
        }

        //Осуществить покупку
        showMessage(this, generateReceipt(moneyAmount));
        Main.activeUser.getBasket().clear();
        thisComponentShown(null);
    }

    //Сгенерировать чек в виде строки
    private static String generateReceipt(double moneyAmount) {
        StringBuilder sb = new StringBuilder();
        sb.append("Ваш чек:\n");
        sb.append("----------\n");
        sb.append("Магазин: ").append(Main.superShop.getName()).append("\n");

        //Текущие дата и время
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);

        sb.append("Дата и время: ").append(formattedDate).append("\n");
        sb.append("----------\n");
        sb.append("Товары:\n");

        for (Product product : Main.activeUser.getBasket().getProducts()) {
            sb.append(String.format("%s: %.2f₸\n", product.getName(), product.getCost()));
        }

        sb.append("----------\n");
        sb.append(String.format("Итого: %.2f₸\n", Main.activeUser.getBasket().getTotalCost()));
        sb.append(String.format("Внесено: %.2f₸\n", moneyAmount));
        sb.append(String.format("Сдача: %.2f₸\n", moneyAmount - Main.activeUser.getBasket().getTotalCost()));
        sb.append("----------\n");
        sb.append("Спасибо за покупку!");
        return sb.toString();
    }

    //Вернуться к главному меню
    private void btnGoBack(ActionEvent e) {
        Main.mainMenuFrame.setLocationRelativeTo(this);
        setVisible(false);
        Main.mainMenuFrame.setVisible(true);
    }

    //Удалить выделенный товар из корзины пользователя
    private void btnRemove(ActionEvent e) {
        if (tbProducts.getSelectedRow() == -1)
            return;

        ProductTableModel model = (ProductTableModel) tbProducts.getModel();

        //Индекс по факту выбранного товара, даже после сортировки
        int selectedProductIndex = tbProducts.convertRowIndexToModel(tbProducts.getSelectedRow());

        //Удалить товар из корзины и из таблицы
        Main.activeUser.getBasket().getProducts().remove(selectedProductIndex);
        model.removeProduct(selectedProductIndex);

        updateTotalCost();
    }

    //Отфильтровать товары по мин. и макс. значениям
    private void btnFilter(ActionEvent e) {
        Main.filterTable(this, tbProducts);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        createUIComponents();

        lblBasket = new JLabel();
        spProducts = new JScrollPane();
        btnRemove = new JButton();
        lblTotalCost = new JLabel();
        btnCheckout = new JButton();
        btnGoBack = new JButton();
        btnFilter = new JButton();

        //======== this ========
        setTitle("\u0412\u0430\u0448\u0430 \u043a\u043e\u0440\u0437\u0438\u043d\u0430");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/res/icon.png")).getImage());
        setResizable(false);
        setMinimumSize(new Dimension(370, 39));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                thisComponentShown(e);
            }
        });
        Container contentPane = getContentPane();

        //---- lblBasket ----
        lblBasket.setText("\u0412\u0430\u0448\u0430 \u043a\u043e\u0440\u0437\u0438\u043d\u0430");
        lblBasket.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        lblBasket.setHorizontalAlignment(SwingConstants.CENTER);

        //======== spProducts ========
        {

            //---- tbProducts ----
            tbProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            spProducts.setViewportView(tbProducts);
        }

        //---- btnRemove ----
        btnRemove.setIcon(new ImageIcon(getClass().getResource("/res/remove.png")));
        btnRemove.setToolTipText("\u0423\u0434\u0430\u043b\u0438\u0442\u044c \u0442\u043e\u0432\u0430\u0440");
        btnRemove.addActionListener(e -> btnRemove(e));

        //---- lblTotalCost ----
        lblTotalCost.setText("\u0418\u0442\u043e\u0433\u043e: _\u20b8");
        lblTotalCost.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        //---- btnCheckout ----
        btnCheckout.setIcon(new ImageIcon(getClass().getResource("/res/checkout.png")));
        btnCheckout.setToolTipText("\u041e\u0444\u043e\u0440\u043c\u0438\u0442\u044c \u0437\u0430\u043a\u0430\u0437");
        btnCheckout.addActionListener(e -> btnCheckout(e));

        //---- btnGoBack ----
        btnGoBack.setIcon(new ImageIcon(getClass().getResource("/res/goBack.png")));
        btnGoBack.setToolTipText("\u041d\u0430\u0437\u0430\u0434");
        btnGoBack.addActionListener(e -> btnGoBack(e));

        //---- btnFilter ----
        btnFilter.setIcon(new ImageIcon(getClass().getResource("/res/filter.png")));
        btnFilter.setToolTipText("\u0424\u0438\u043b\u044c\u0442\u0440\u0430\u0446\u0438\u044f");
        btnFilter.addActionListener(e -> btnFilter(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(lblBasket, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(lblTotalCost, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnGoBack, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnFilter, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnCheckout, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
                .addComponent(spProducts, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblBasket)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(spProducts, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(0, 2, Short.MAX_VALUE)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(btnCheckout)
                                .addComponent(btnRemove)
                                .addComponent(btnFilter)
                                .addComponent(btnGoBack)))
                        .addComponent(lblTotalCost, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(0, 3, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel lblBasket;
    private JScrollPane spProducts;
    private JTable tbProducts;
    private JButton btnRemove;
    private JLabel lblTotalCost;
    private JButton btnCheckout;
    private JButton btnGoBack;
    private JButton btnFilter;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
