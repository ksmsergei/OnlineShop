package com.lab6;

import com.lab6.frames.*;
import com.lab6.frames.dialogs.DialogResult;
import com.lab6.frames.dialogs.LookAndFeelDialog;
import com.lab6.frames.dialogs.MinMaxDialog;
import com.lab6.frames.tablemodels.ProductTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.*;

public class Main {
    private static final String SHOP_SER_PATH = "superShop.bin";

    public static final String LOGIN_REGEX = "^[a-zA-Z0-9._-]{3,16}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9!@#$%^&*()_+]{8,16}$";

    public static Shop superShop;
    public static User activeUser;

    public static LoginFrame loginFrame;
    public static RegisterFrame registerFrame;
    public static MainMenuFrame mainMenuFrame;
    public static ShowBasketFrame showBasketFrame;
    public static ShowProductsFrame showProductsFrame;
    public static ShowCategoriesFrame showCategoriesFrame;

    //Просто показать какое-то сообщение
    public static void showMessage(Component comp, String msg) {
        JOptionPane.showMessageDialog(comp, msg, superShop.getName(), JOptionPane.INFORMATION_MESSAGE);
    }

    //Функции для уменьшения повторения кода
    public static JTable createTable() {
        JTable table = new JTable();

        //Запретить передвигать столбцы
        table.getTableHeader().setReorderingAllowed(false);

        //Разрешить сортировку
        table.setAutoCreateRowSorter(true);

        //Центрировать содержимое ячеек для типов Integer и Double
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.setDefaultRenderer(Integer.class, centerRenderer);
        table.setDefaultRenderer(Double.class, centerRenderer);

        return table;
    }

    public static void prepareTable(JTable table, int ... colWidths) {
        //Запретить сортировку первого столбца (№)
        ((DefaultRowSorter<?, ?>) table.getRowSorter()).setSortable(0, false);

        //Задать размеры столбцов по умолчанию
        for (int i = 0; i < colWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(colWidths[i]);
        }
    }

    public static void filterTable(JFrame frame, JTable table) {
        MinMaxDialog.MinMaxDialogResult dialogResult = MinMaxDialog.show(frame);

        if (dialogResult.result == DialogResult.CANCEL)
            return;

        //Дальше идёт RowSorter, который, собственно, и отвечает за сортировку
        @SuppressWarnings("unchecked")
        TableRowSorter<ProductTableModel> sorter = (TableRowSorter<ProductTableModel>) table.getRowSorter();

        RowFilter<ProductTableModel, Integer> MinMax = new RowFilter<ProductTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends ProductTableModel, ? extends Integer> entry) {
                Product product = entry.getModel().getProduct(entry.getIdentifier());

                if (dialogResult.filterIndex == 0) {
                    //Фильтрация по цене
                    return product.getCost() >= dialogResult.minLimit && product.getCost() <= dialogResult.maxLimit;
                } else if (dialogResult.filterIndex == 1) {
                    //Фильтрация по рейтингу
                    return product.getRating() >= dialogResult.minLimit && product.getRating() <= dialogResult.maxLimit;
                }

                return false;
            }
        };

        sorter.setRowFilter(MinMax);
    }
    //--------------

    private static Category fillCategory(Category category, int start, int end) {
        for (int i = start; i <= end; i++) {
            category.addProduct(superShop.getProduct(i));
        }

        return category;
    }

    //Создать магазин, заполнить его товарами, категориями и пользователями
    private static void prepareShop() {

        //Пытаемся десериализовать магазин из файла
        try (FileInputStream fileInputStream = new FileInputStream(SHOP_SER_PATH);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            superShop = (Shop) objectInputStream.readObject();
            return;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            //Если файл с сохранёнными данными не был найден, то реализовать магазин по умолчанию
        }

        //Создаём наш магазин
        superShop = new Shop("Супер Магазин");

        //Добавляем в магазин все продаваемые товары

        //Овощи и фрукты
        superShop.addProduct(new Product("Помидоры", 500, 4.5));     //0
        superShop.addProduct(new Product("Огурцы", 400, 4.0));       //1
        superShop.addProduct(new Product("Бананы", 700, 4.8));       //2
        superShop.addProduct(new Product("Яблоки", 600, 4.7));       //3
        superShop.addProduct(new Product("Картофель", 300, 4.0));    //4
        superShop.addProduct(new Product("Морковь", 350, 4.2));      //5
        superShop.addProduct(new Product("Лимоны", 1000, 4.6));      //6
        superShop.addProduct(new Product("Чеснок", 1200, 4.4));      //7
        superShop.addProduct(new Product("Апельсины", 800, 4.5));    //8
        superShop.addProduct(new Product("Салат", 600, 4.3));        //9
        superShop.addCategory(fillCategory(new Category("Овощи и фрукты"), 0, 9));

        //Молочные продукты
        superShop.addProduct(new Product("Молоко", 400, 4.0));               //10
        superShop.addProduct(new Product("Сыр", 3000, 4.7));                 //11
        superShop.addProduct(new Product("Творог", 1200, 4.2));              //12
        superShop.addProduct(new Product("Йогурт", 800, 4.3));               //13
        superShop.addProduct(new Product("Сливки", 1000, 4.5));              //14
        superShop.addProduct(new Product("Масло сливочное", 2500, 4.4));     //15
        superShop.addProduct(new Product("Кефир", 600, 4.1));                //16
        superShop.addProduct(new Product("Яйца", 350, 4.0));                 //17
        superShop.addProduct(new Product("Сметана", 1000, 4.3));             //18
        superShop.addProduct(new Product("Йогуртовые напитки", 500, 4.2));   //19
        superShop.addCategory(fillCategory(new Category("Молочные продукты"), 10, 19));

        //Мясо и птица
        superShop.addProduct(new Product("Куриное филе", 1000, 4.5));     //20
        superShop.addProduct(new Product("Свинина", 2000, 4.3));          //21
        superShop.addProduct(new Product("Говядина", 3000, 4.6));         //22
        superShop.addProduct(new Product("Колбаса", 3500, 4.4));          //23
        superShop.addProduct(new Product("Куриные яйца", 350, 4.0));      //24
        superShop.addProduct(new Product("Бекон", 2500, 4.2));            //25
        superShop.addProduct(new Product("Телятина", 4000, 4.7));         //26
        superShop.addProduct(new Product("Куриные крылья", 1500, 4.1));   //27
        superShop.addProduct(new Product("Копчености", 3000, 4.3));       //28
        superShop.addProduct(new Product("Индейка", 2500, 4.5));          //29
        superShop.addCategory(fillCategory(new Category("Мясо и птица"), 20, 29));

        //Хлебобулочные изделия
        superShop.addProduct(new Product("Белый хлеб", 300, 4.0));            //30
        superShop.addProduct(new Product("Черный хлеб", 400, 4.1));           //31
        superShop.addProduct(new Product("Булки", 150, 4.0));                 //32
        superShop.addProduct(new Product("Багет", 500, 4.2));                 //33
        superShop.addProduct(new Product("Сдоба", 600, 4.3));                 //34
        superShop.addProduct(new Product("Тостовый хлеб", 350, 4.0));         //35
        superShop.addProduct(new Product("Пита", 250, 4.0));                  //36
        superShop.addProduct(new Product("Хлебцы", 400, 4.1));                //37
        superShop.addProduct(new Product("Круассаны", 700, 4.4));             //38
        superShop.addProduct(new Product("Ржаной хлеб", 450, 4.2));           //39
        superShop.addCategory(fillCategory(new Category("Хлебобулочные изделия"), 30, 39));

        //Кондитерские изделия и сладости
        superShop.addProduct(new Product("Шоколад", 1000, 4.5));               //40
        superShop.addProduct(new Product("Конфеты", 1500, 4.4));               //41
        superShop.addProduct(new Product("Печенье", 800, 4.2));                //42
        superShop.addProduct(new Product("Торт", 5000, 4.7));                  //43
        superShop.addProduct(new Product("Мороженое", 1000, 4.3));             //44
        superShop.addProduct(new Product("Пряники", 1200, 4.1));               //45
        superShop.addProduct(new Product("Мармелад", 700, 4.0));               //46
        superShop.addProduct(new Product("Желе", 500, 4.0));                   //47
        superShop.addProduct(new Product("Пастила", 900, 4.0));                //48
        superShop.addProduct(new Product("Шоколадные батончики", 600, 4.2));   //49
        superShop.addCategory(fillCategory(new Category("Кондитерские изделия и сладости"), 40, 49));

        //Напитки
        superShop.addProduct(new Product("Минеральная вода", 300, 4.0));         //50
        superShop.addProduct(new Product("Газированные напитки", 400, 3.9));     //51
        superShop.addProduct(new Product("Соки", 800, 4.2));                     //52
        superShop.addProduct(new Product("Чай", 1000, 4.3));                     //53
        superShop.addProduct(new Product("Кофе", 2000, 4.5));                    //54
        superShop.addProduct(new Product("Энергетические напитки", 1500, 4.1));  //55
        superShop.addProduct(new Product("Лимонад", 600, 4.0));                  //56
        superShop.addProduct(new Product("Квас", 700, 4.2));                     //57
        superShop.addProduct(new Product("Спортивные напитки", 1000, 4.0));      //58
        superShop.addProduct(new Product("Вино", 3000, 4.4));                    //59
        superShop.addCategory(fillCategory(new Category("Напитки"), 50, 59));

        //Создаём аккаунт администратора
        User admin = new User("admin", "admin", true);
        superShop.addUser(admin);
    }

    //Выбор Look And Feel (только при аргументе -lf)
    private static void selectLookAndFeel(String[] args) {
        if (args.length == 0)
            return;

        if (!args[0].equals("-lf"))
            return;

        final String className;

        //Если аргумент только -lf, то показать диалоговое окно выбора
        if (args.length == 1) {
            LookAndFeelDialog.LookAndFeelDialogResult dialogResult = LookAndFeelDialog.show(null);

            if (dialogResult.result != DialogResult.OK) {
                return;
            }

            className = dialogResult.className;
        } else {
            //Если аргументов как минимум два, и первый -lf, то второй будет указывать полное имя класса Look And Feel
            className = args[1];
        }

        try {
            UIManager.setLookAndFeel(className);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Look And Feel not found!");
        }
    }

    public static void main(String[] args) {
        prepareShop();

        //Look and Feel по умолчанию - Nimbus
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            //Игнорировать если недоступен
        }

        SwingUtilities.invokeLater (() -> {
            selectLookAndFeel(args);

            UIManager.put("OptionPane.yesButtonText", "Да");
            UIManager.put("OptionPane.noButtonText", "Нет");

            //При завершении программы попробовать сериализовать объект магазина
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try (FileOutputStream outputStream = new FileOutputStream(SHOP_SER_PATH);
                     ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {

                    objectOutputStream.writeObject(superShop);
                } catch (IOException e) {
                    //Если не получилось сериализовать объект, то ничего не предпринимать
                }
            }));

            loginFrame = new LoginFrame();
            registerFrame = new RegisterFrame();
            mainMenuFrame = new MainMenuFrame();
            showBasketFrame = new ShowBasketFrame();
            showProductsFrame = new ShowProductsFrame();
            showCategoriesFrame = new ShowCategoriesFrame();

            loginFrame.setVisible(true);
        });
    }
}