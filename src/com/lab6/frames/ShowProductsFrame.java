/*
 * Created by JFormDesigner on Fri Mar 22 05:53:23 QYZT 2024
 */

package com.lab6.frames;

import com.lab6.Category;
import com.lab6.Main;
import com.lab6.Product;
import com.lab6.frames.dialogs.SelectProductDialog;
import com.lab6.frames.tablemodels.CountedProductTableModel;
import com.lab6.frames.dialogs.CreateProductDialog;
import com.lab6.frames.tablemodels.ProductTableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.table.TableRowSorter;

/**
 * @author Сергей
 */
public class ShowProductsFrame extends JFrame {
    public ShowProductsFrame() {
        initComponents();
    }

    private JFrame previousFrame;
    private Category currentCategory;

    //Вызвать это окно в качестве выбора из всех товаров магазина
    public void showAllProducts() {
        //Указать прошлое окно, к которому стоит возвращаться
        previousFrame = Main.mainMenuFrame;

        //Очистить прошлые данные и заполнить таблицу
        CountedProductTableModel model = new CountedProductTableModel(Main.superShop.getProducts());
        model.setTable(tbProducts);
        tbProducts.setModel(model);
        setVisible(true);

        //Подходящие заголовки
        lblProducts.setText("Все продукты");
        setTitle("Все продукты");

        //Показывать кнопку "Добавить товар в текущую категорию", если пользователь админ
        btnAdminInsert.setVisible(false);
        btnAdminCreate.setVisible(Main.activeUser.isAdmin());
    }

    //Вызвать это окно в качестве выбора из товаров категории
    public void showCategory(Category category) {
        //Указать прошлое окно, к которому стоит возвращаться
        previousFrame = Main.showCategoriesFrame;

        currentCategory = category;

        //Очистить прошлые данные и заполнить таблицу
        CountedProductTableModel model = new CountedProductTableModel(category.getProducts());
        model.setTable(tbProducts);
        tbProducts.setModel(model);
        setVisible(true);

        //Подходящие заголовки
        lblProducts.setText(category.getName());
        setTitle(category.getName());

        //Показывать кнопку "Создать товар", если пользователь админ
        btnAdminCreate.setVisible(false);
        btnAdminInsert.setVisible(Main.activeUser.isAdmin());


    }

    private void thisComponentShown(ComponentEvent e) {
        Main.prepareTable(tbProducts, 25, this.getWidth() - 25 - 65 - 65 - 75, 65, 65, 75);
        pack();
    }

    //Вернуться к главному меню
    private void btnGoBack(ActionEvent e) {
        previousFrame.setLocationRelativeTo(this);
        setVisible(false);
        previousFrame.setVisible(true);
    }

    //Отфильтровать товары по мин. и макс. значениям
    private void btnFilter(ActionEvent e) {
        Main.filterTable(this, tbProducts);
    }

    private void createUIComponents() {
        tbProducts = Main.createTable();
    }

    //Добавить выбранный товар в корзину
    private void btnAddProduct(ActionEvent e) {
        if (tbProducts.getSelectedRow() == -1)
            return;

        int selectedIndex = tbProducts.convertRowIndexToModel(tbProducts.getSelectedRow());
        ProductTableModel model = (ProductTableModel) tbProducts.getModel();
        Product selectedProduct = model.getProduct(selectedIndex);
        Main.activeUser.getBasket().addProduct(selectedProduct);

        //Обновить счётчик "В корзине"
        model.refreshCell(selectedIndex, 4);
    }

    //Только для админа: создать новый товар и добавить в каталог магазина
    private void btnAdminCreate(ActionEvent e) {
        if (!Main.activeUser.isAdmin()) {
            return;
        }

        CreateProductDialog.CreateProductDialogResult dialogResult = CreateProductDialog.show(this);

        switch (dialogResult.result) {
            case CANCEL:
                return;

            case INCORRECT_INPUT:
                Main.showMessage(this, "Были введены некорректные данные.");
                return;
        }

        Product newProduct = dialogResult.product;

        //Добавить продукт в каталог магазина
        Main.superShop.addProduct(newProduct);

        //Добавить продукт в таблицу
        ProductTableModel model = (ProductTableModel) tbProducts.getModel();
        model.addProduct(newProduct);
    }

    //Выбрать товар и добавить в текущую категорию
    private void btnAdminInsert(ActionEvent e) {
        if (!Main.activeUser.isAdmin()) {
            return;
        }

        //Все товары, которые есть в магазине, но отсутствуют в текущей категории
        ArrayList<Product> allMissingProducts = new ArrayList<>(Main.superShop.getProducts());
        allMissingProducts.removeAll(currentCategory.getProducts());

        if (allMissingProducts.isEmpty()) {
            Main.showMessage(this, "В этой категории содержаться все доступные товары.");
            return;
        }

        SelectProductDialog.SelectProductDialogResult dialogResult = SelectProductDialog.show(this, allMissingProducts);

        switch (dialogResult.result) {
            case CANCEL:
                return;

            case INCORRECT_INPUT:
                Main.showMessage(this, "Выбран некорректный товар.");
                return;
        }

        //Добавить продукт в текущую категорию
        currentCategory.addProduct(dialogResult.product);

        //Добавить продукт в таблицу
        ProductTableModel model = (ProductTableModel) tbProducts.getModel();
        model.addProduct(dialogResult.product);
    }

    //Осуществить фильтрацию по имени
    private void btnSearch(ActionEvent e) {
        String searchBy = JOptionPane.showInputDialog(this, "Введите часть названия товара:", Main.superShop.getName(), JOptionPane.INFORMATION_MESSAGE);

        @SuppressWarnings("unchecked")
        TableRowSorter<CountedProductTableModel> sorter = (TableRowSorter<CountedProductTableModel>) tbProducts.getRowSorter();

        RowFilter<CountedProductTableModel, Integer> Search = new RowFilter<CountedProductTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends CountedProductTableModel, ? extends Integer> entry) {
                Product product = entry.getModel().getProduct(entry.getIdentifier());

                //Если в строке содержится искомая подстрока, то показать эту строчку (без учёта регистра)
                return product.getName().toLowerCase().contains(searchBy.toLowerCase());
            }
        };

        sorter.setRowFilter(Search);
    }

    //Добавить товар в корзину по дабл клику
    private void tbProductsMouseClicked(MouseEvent e) {
        if (e.getClickCount() % 2 == 0) {
            btnAddProduct(null);
        }
    }

    //Добавить товар в корзину по нажатию на пробел
    private void tbProductsKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == 32) {
            btnAddProduct(null);
        }
    }

    //Выделять строки таблицы с помощью правого клика мыши
    private void tbProductsMousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3) {
            int row = tbProducts.rowAtPoint(e.getPoint());

            if (row != -1) {
                tbProducts.setRowSelectionInterval(row, row);
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        createUIComponents();

        lblProducts = new JLabel();
        spProducts = new JScrollPane();
        btnGoBack = new JButton();
        btnFilter = new JButton();
        btnAddProduct = new JButton();
        btnAdminCreate = new JButton();
        btnAdminInsert = new JButton();
        btnSearch = new JButton();
        pmProducts = new JPopupMenu();
        miAddProduct = new JMenuItem();

        //======== this ========
        setTitle("\u0412\u044b\u0431\u0435\u0440\u0438\u0442\u0435 \u043f\u0440\u043e\u0434\u0443\u043a\u0442\u044b");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/res/icon.png")).getImage());
        setMinimumSize(new Dimension(370, 39));
        setResizable(false);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                thisComponentShown(e);
            }
        });
        Container contentPane = getContentPane();

        //---- lblProducts ----
        lblProducts.setText("\u0412\u044b\u0431\u0435\u0440\u0438\u0442\u0435 \u043f\u0440\u043e\u0434\u0443\u043a\u0442\u044b");
        lblProducts.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        lblProducts.setHorizontalAlignment(SwingConstants.CENTER);

        //======== spProducts ========
        {

            //---- tbProducts ----
            tbProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tbProducts.setComponentPopupMenu(pmProducts);
            tbProducts.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    tbProductsMouseClicked(e);
                }
                @Override
                public void mousePressed(MouseEvent e) {
                    tbProductsMousePressed(e);
                }
            });
            tbProducts.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    tbProductsKeyReleased(e);
                }
            });
            spProducts.setViewportView(tbProducts);
        }

        //---- btnGoBack ----
        btnGoBack.setIcon(new ImageIcon(getClass().getResource("/res/goBack.png")));
        btnGoBack.setToolTipText("\u041d\u0430\u0437\u0430\u0434");
        btnGoBack.addActionListener(e -> btnGoBack(e));

        //---- btnFilter ----
        btnFilter.setIcon(new ImageIcon(getClass().getResource("/res/filter.png")));
        btnFilter.setToolTipText("\u0424\u0438\u043b\u044c\u0442\u0440\u0430\u0446\u0438\u044f");
        btnFilter.addActionListener(e -> btnFilter(e));

        //---- btnAddProduct ----
        btnAddProduct.setIcon(new ImageIcon(getClass().getResource("/res/add.png")));
        btnAddProduct.setToolTipText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0432 \u043a\u043e\u0440\u0437\u0438\u043d\u0443");
        btnAddProduct.addActionListener(e -> btnAddProduct(e));

        //---- btnAdminCreate ----
        btnAdminCreate.setIcon(new ImageIcon(getClass().getResource("/res/create.png")));
        btnAdminCreate.setToolTipText("(\u0410\u0434\u043c\u0438\u043d) \u0421\u043e\u0437\u0434\u0430\u0442\u044c \u0442\u043e\u0432\u0430\u0440");
        btnAdminCreate.addActionListener(e -> btnAdminCreate(e));

        //---- btnAdminInsert ----
        btnAdminInsert.setIcon(new ImageIcon(getClass().getResource("/res/insert.png")));
        btnAdminInsert.setToolTipText("(\u0410\u0434\u043c\u0438\u043d) \u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0442\u043e\u0432\u0430\u0440 \u0432 \u0442\u0435\u043a\u0443\u0449\u0443\u044e \u043a\u0430\u0442\u0435\u0433\u043e\u0440\u0438\u044e");
        btnAdminInsert.addActionListener(e -> btnAdminInsert(e));

        //---- btnSearch ----
        btnSearch.setIcon(new ImageIcon(getClass().getResource("/res/search.png")));
        btnSearch.setToolTipText("\u041d\u0430\u0437\u0430\u0434");
        btnSearch.addActionListener(e -> btnSearch(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(lblProducts, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(btnAdminCreate, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnAdminInsert, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                            .addComponent(btnGoBack, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnFilter, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnAddProduct, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
                .addComponent(spProducts, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblProducts)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(spProducts, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(btnAddProduct)
                        .addComponent(btnFilter)
                        .addComponent(btnAdminCreate)
                        .addComponent(btnAdminInsert)
                        .addComponent(btnSearch)
                        .addComponent(btnGoBack))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());

        //======== pmProducts ========
        {

            //---- miAddProduct ----
            miAddProduct.setText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0432 \u043a\u043e\u0440\u0437\u0438\u043d\u0443");
            miAddProduct.addActionListener(e -> btnAddProduct(e));
            pmProducts.add(miAddProduct);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel lblProducts;
    private JScrollPane spProducts;
    private JTable tbProducts;
    private JButton btnGoBack;
    private JButton btnFilter;
    private JButton btnAddProduct;
    private JButton btnAdminCreate;
    private JButton btnAdminInsert;
    private JButton btnSearch;
    private JPopupMenu pmProducts;
    private JMenuItem miAddProduct;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
