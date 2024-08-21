/*
 * Created by JFormDesigner on Fri Mar 22 07:47:10 QYZT 2024
 */

package com.lab6.frames;

import com.lab6.Category;
import com.lab6.Main;
import com.lab6.frames.dialogs.CreateCategoryDialog;
import com.lab6.frames.tablemodels.CategoryTableModel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.table.TableRowSorter;

/**
 * @author Сергей
 */
public class ShowCategoriesFrame extends JFrame {
    public ShowCategoriesFrame() {
        initComponents();
    }

    //Заполнение таблицы нужными данными
    private void thisComponentShown(ComponentEvent e) {
        //Кнопка создания новой категории видна только админам
        btnAdminCreate.setVisible(Main.activeUser.isAdmin());

        //Очистить прошлые данные и заполнить таблицу
        CategoryTableModel model = new CategoryTableModel(Main.superShop.getCategories());
        model.setTable(tbCategories);
        tbCategories.setModel(model);

        Main.prepareTable(tbCategories, 25, this.getWidth() - 25);
    }

    //Вернуться к главному меню
    private void btnGoBack(ActionEvent e) {
        Main.mainMenuFrame.setLocationRelativeTo(this);
        setVisible(false);
        Main.mainMenuFrame.setVisible(true);
    }

    private void createUIComponents() {
        tbCategories = Main.createTable();
    }

    //Войти в выбранную категорию
    private void btnEnter(ActionEvent e) {
        if (tbCategories.getSelectedRow() == -1)
            return;

        int selectedIndex = tbCategories.convertRowIndexToModel(tbCategories.getSelectedRow());
        CategoryTableModel model = (CategoryTableModel) tbCategories.getModel();
        Category selectedCategory = model.getCategory(selectedIndex);

        //Вызвать окно выбора товаров и заполнить таблицу товарами выбранной категории
        Main.showProductsFrame.setLocationRelativeTo(this);
        setVisible(false);
        Main.showProductsFrame.showCategory(selectedCategory);
    }

    //Только для админа: создать новую категорию товар и добавить в каталог магазина
    private void btnAdminCreate(ActionEvent e) {
        if (!Main.activeUser.isAdmin()) {
            return;
        }

        CreateCategoryDialog.CreateCategoryDialogResult dialogResult = CreateCategoryDialog.show(this);

        switch (dialogResult.result) {
            case CANCEL:
                return;

            case INCORRECT_INPUT:
                Main.showMessage(this, "Были введены некорректные данные.");
                return;
        }

        Category newCategory = dialogResult.category;

        //Добавить категорию в каталог магазина
        Main.superShop.addCategory(newCategory);

        //Добавить категорию в таблицу
        CategoryTableModel model = (CategoryTableModel) tbCategories.getModel();
        model.addCategory(newCategory);
    }

    //Осуществить фильтрацию по имени
    private void btnSearch(ActionEvent e) {
        String searchBy = JOptionPane.showInputDialog(this, "Введите часть названия категории:", Main.superShop.getName(), JOptionPane.INFORMATION_MESSAGE);

        @SuppressWarnings("unchecked")
        TableRowSorter<CategoryTableModel> sorter = (TableRowSorter<CategoryTableModel>) tbCategories.getRowSorter();

        RowFilter<CategoryTableModel, Integer> Search = new RowFilter<CategoryTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends CategoryTableModel, ? extends Integer> entry) {
                Category category = entry.getModel().getCategory(entry.getIdentifier());

                //Если в строке содержится искомая подстрока, то показать эту строчку (без учёта регистра)
                return category.getName().toLowerCase().contains(searchBy.toLowerCase());
            }
        };

        sorter.setRowFilter(Search);
    }

    //Зайти в категорию по дабл клику
    private void tbCategoriesMouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            btnEnter(null);
        }
    }

    //Зайти в категорию по нажатию пробела
    private void tbCategoriesKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == 32) {
            btnEnter(null);
        }
    }

    //Выделять строки таблицы с помощью правого клика мыши
    private void tbCategoriesMousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3) {
            int row = tbCategories.rowAtPoint(e.getPoint());

            if (row != -1) {
                tbCategories.setRowSelectionInterval(row, row);
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        createUIComponents();

        lblCategories = new JLabel();
        spCategories = new JScrollPane();
        btnGoBack = new JButton();
        btnEnter = new JButton();
        btnAdminCreate = new JButton();
        btnSearch = new JButton();
        pmCategories = new JPopupMenu();
        miEnter = new JMenuItem();

        //======== this ========
        setTitle("\u0412\u044b\u0431\u0435\u0440\u0438\u0442\u0435 \u043a\u0430\u0442\u0435\u0433\u043e\u0440\u0438\u044e");
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

        //---- lblCategories ----
        lblCategories.setText("\u0412\u044b\u0431\u0435\u0440\u0438\u0442\u0435 \u043a\u0430\u0442\u0435\u0433\u043e\u0440\u0438\u044e");
        lblCategories.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        lblCategories.setHorizontalAlignment(SwingConstants.CENTER);

        //======== spCategories ========
        {

            //---- tbCategories ----
            tbCategories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tbCategories.setComponentPopupMenu(pmCategories);
            tbCategories.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    tbCategoriesMouseClicked(e);
                }
                @Override
                public void mousePressed(MouseEvent e) {
                    tbCategoriesMousePressed(e);
                }
            });
            tbCategories.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    tbCategoriesKeyReleased(e);
                }
            });
            spCategories.setViewportView(tbCategories);
        }

        //---- btnGoBack ----
        btnGoBack.setIcon(new ImageIcon(getClass().getResource("/res/goBack.png")));
        btnGoBack.setToolTipText("\u041d\u0430\u0437\u0430\u0434");
        btnGoBack.addActionListener(e -> btnGoBack(e));

        //---- btnEnter ----
        btnEnter.setIcon(new ImageIcon(getClass().getResource("/res/enter.png")));
        btnEnter.setToolTipText("\u0412\u043e\u0439\u0442\u0438");
        btnEnter.addActionListener(e -> btnEnter(e));

        //---- btnAdminCreate ----
        btnAdminCreate.setIcon(new ImageIcon(getClass().getResource("/res/create.png")));
        btnAdminCreate.setToolTipText("(\u0410\u0434\u043c\u0438\u043d) \u0421\u043e\u0437\u0434\u0430\u0442\u044c \u043a\u0430\u0442\u0435\u0433\u043e\u0440\u0438\u044e");
        btnAdminCreate.addActionListener(e -> btnAdminCreate(e));

        //---- btnSearch ----
        btnSearch.setIcon(new ImageIcon(getClass().getResource("/res/search.png")));
        btnSearch.setToolTipText("\u041f\u043e\u0438\u0441\u043a");
        btnSearch.addActionListener(e -> btnSearch(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(lblCategories, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(btnAdminCreate, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                            .addComponent(btnGoBack, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnEnter, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
                .addComponent(spCategories, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblCategories)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(spCategories, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(btnEnter)
                        .addComponent(btnAdminCreate)
                        .addComponent(btnSearch)
                        .addComponent(btnGoBack))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());

        //======== pmCategories ========
        {

            //---- miEnter ----
            miEnter.setText("\u0412\u043e\u0439\u0442\u0438");
            miEnter.addActionListener(e -> btnEnter(e));
            pmCategories.add(miEnter);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel lblCategories;
    private JScrollPane spCategories;
    private JTable tbCategories;
    private JButton btnGoBack;
    private JButton btnEnter;
    private JButton btnAdminCreate;
    private JButton btnSearch;
    private JPopupMenu pmCategories;
    private JMenuItem miEnter;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
