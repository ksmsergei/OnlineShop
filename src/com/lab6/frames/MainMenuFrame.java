/*
 * Created by JFormDesigner on Wed Mar 20 22:44:57 QYZT 2024
 */

package com.lab6.frames;

import java.awt.*;
import com.lab6.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Сергей
 */
public class MainMenuFrame extends JFrame {
    public MainMenuFrame() {
        initComponents();
    }

    private void thisComponentShown(ComponentEvent e) {
        lblWelcome.setText(String.format("Добро пожаловать, %s!", Main.activeUser.getLogin()));

        //Автоматически изменить размер шрифта, чтобы ник полностью помещался в окно
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 24));

        Font labelFont = lblWelcome.getFont();
        String labelText = lblWelcome.getText();

        int stringWidth = lblWelcome.getFontMetrics(labelFont).stringWidth(labelText);
        int componentWidth = lblWelcome.getWidth() - 75;

        // Find out how much the font can grow in width.
        double widthRatio = (double)componentWidth / (double)stringWidth;

        int newFontSize = (int)(labelFont.getSize() * widthRatio) - 1;
        int componentHeight = lblWelcome.getHeight();

        // Pick a new font size, so it will not be larger than the height of label.
        int fontSizeToUse = Math.min(newFontSize, componentHeight);

        // Set the label's font size to the newly determined size.
        lblWelcome.setFont(new Font(labelFont.getName(), Font.BOLD, fontSizeToUse));
    }

    //Выйти из аккаунта
    private void btnLogout(ActionEvent e) {
        Main.activeUser = null;
        Main.loginFrame.setLocationRelativeTo(this);
        setVisible(false);
        Main.loginFrame.setVisible(true);
    }

    //Просмотреть свою корзину
    private void btnBasket(ActionEvent e) {
        Main.showBasketFrame.setLocationRelativeTo(this);
        setVisible(false);
        Main.showBasketFrame.setVisible(true);
    }

    //Просмотреть все продукты
    private void btnProducts(ActionEvent e) {
        Main.showBasketFrame.setLocationRelativeTo(this);
        setVisible(false);
        Main.showProductsFrame.showAllProducts();
    }

    //Просмотреть все категории
    private void btnCategories(ActionEvent e) {
        Main.showBasketFrame.setLocationRelativeTo(this);
        setVisible(false);
        Main.showCategoriesFrame.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        lblWelcome = new JLabel();
        pnlButtons = new JPanel();
        btnCategories = new JButton();
        btnProducts = new JButton();
        btnBasket = new JButton();
        btnLogout = new JButton();

        //======== this ========
        setTitle("\u0413\u043b\u0430\u0432\u043d\u043e\u0435 \u043c\u0435\u043d\u044e");
        setIconImage(new ImageIcon(getClass().getResource("/res/icon.png")).getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                thisComponentShown(e);
            }
        });
        Container contentPane = getContentPane();

        //---- lblWelcome ----
        lblWelcome.setText("\u0414\u043e\u0431\u0440\u043e \u043f\u043e\u0436\u0430\u043b\u043e\u0432\u0430\u0442\u044c, _!");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblWelcome.setIcon(new ImageIcon(getClass().getResource("/res/icon.png")));
        lblWelcome.setIconTextGap(15);
        lblWelcome.setMinimumSize(new Dimension(486, 64));
        lblWelcome.setPreferredSize(new Dimension(486, 64));

        //======== pnlButtons ========
        {
            pnlButtons.setLayout(new GridLayout(2, 2, 10, 10));

            //---- btnCategories ----
            btnCategories.setText("\u041f\u0440\u043e\u0441\u043c\u043e\u0442\u0440\u0435\u0442\u044c \u043a\u0430\u0442\u0435\u0433\u043e\u0440\u0438\u0438");
            btnCategories.setIcon(new ImageIcon(getClass().getResource("/res/category.png")));
            btnCategories.setHorizontalTextPosition(SwingConstants.CENTER);
            btnCategories.setVerticalTextPosition(SwingConstants.BOTTOM);
            btnCategories.setPreferredSize(new Dimension(137, 50));
            btnCategories.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            btnCategories.addActionListener(e -> btnCategories(e));
            pnlButtons.add(btnCategories);

            //---- btnProducts ----
            btnProducts.setText("\u0412\u044b\u0431\u0440\u0430\u0442\u044c \u043f\u0440\u043e\u0434\u0443\u043a\u0442\u044b");
            btnProducts.setIcon(new ImageIcon(getClass().getResource("/res/product.png")));
            btnProducts.setHorizontalTextPosition(SwingConstants.CENTER);
            btnProducts.setVerticalTextPosition(SwingConstants.BOTTOM);
            btnProducts.setPreferredSize(new Dimension(137, 50));
            btnProducts.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            btnProducts.addActionListener(e -> btnProducts(e));
            pnlButtons.add(btnProducts);

            //---- btnBasket ----
            btnBasket.setText("\u041a\u043e\u0440\u0437\u0438\u043d\u0430");
            btnBasket.setIcon(new ImageIcon(getClass().getResource("/res/basket.png")));
            btnBasket.setHorizontalTextPosition(SwingConstants.CENTER);
            btnBasket.setVerticalTextPosition(SwingConstants.BOTTOM);
            btnBasket.setIconTextGap(-2);
            btnBasket.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            btnBasket.addActionListener(e -> btnBasket(e));
            pnlButtons.add(btnBasket);

            //---- btnLogout ----
            btnLogout.setText("\u0412\u044b\u0439\u0442\u0438");
            btnLogout.setIcon(new ImageIcon(getClass().getResource("/res/logout.png")));
            btnLogout.setHorizontalTextPosition(SwingConstants.CENTER);
            btnLogout.setVerticalTextPosition(SwingConstants.BOTTOM);
            btnLogout.setIconTextGap(-2);
            btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            btnLogout.addActionListener(e -> btnLogout(e));
            pnlButtons.add(btnLogout);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(lblWelcome, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlButtons, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblWelcome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(pnlButtons, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel lblWelcome;
    private JPanel pnlButtons;
    private JButton btnCategories;
    private JButton btnProducts;
    private JButton btnBasket;
    private JButton btnLogout;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
