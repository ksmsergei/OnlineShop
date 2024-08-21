/*
 * Created by JFormDesigner on Wed Mar 20 21:20:48 QYZT 2024
 */

package com.lab6.frames;

import com.lab6.*;
import static com.lab6.Main.showMessage;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author Сергей
 */
public class RegisterFrame extends JFrame {
    private final Color LINK_DEFAULT_COLOR = new Color(0x5E87C8);
    private final Color LINK_HOVER_COLOR = new Color(0x82A2D4);

    private char defaultEchoChar;

    public RegisterFrame() {
        initComponents();

        defaultEchoChar = pfPassword.getEchoChar();
    }

    //Показать или скрыть пароли при клике на чекбокс "Показать пароль"
    private void cbShowPassword(ActionEvent e) {
        if (cbShowPassword.isSelected()) {
            pfPassword.setEchoChar((char) 0);
            pfPasswordAgain.setEchoChar((char) 0);
        } else {
            pfPassword.setEchoChar(defaultEchoChar);
            pfPasswordAgain.setEchoChar(defaultEchoChar);
        }
    }

    private void lblAuthMouseEntered(MouseEvent e) {
        lblAuth.setForeground(LINK_HOVER_COLOR);
    }

    private void lblAuthMouseExited(MouseEvent e) {
        lblAuth.setForeground(LINK_DEFAULT_COLOR);
    }

    //При нажатии на лейбл "Вернуться к авторизации", открыть окно авторизации
    private void lblAuthMouseReleased(MouseEvent e) {
        Main.loginFrame.setLocationRelativeTo(this);
        setVisible(false);
        Main.loginFrame.setVisible(true);
    }

    //Нажатие на кнопку "Зарегистрироваться", попытка регистрации
    private void btnRegister(ActionEvent e) {
        String password = String.valueOf(pfPassword.getPassword());
        String passwordAgain = String.valueOf(pfPasswordAgain.getPassword());


        //Подтверждение корректности пароля
        if (!password.equals(passwordAgain)) {
            showMessage(this, "Пароли не совпадают!");
            return;
        }

        String login = tfLogin.getText();

        //Требования к логину
        if (!Pattern.matches(Main.LOGIN_REGEX, login)) {
            showMessage(this, "Требования к логину:\n" +
                    "1) должен содержать от 3 до 16 символов\n" +
                    "2) состоять из букв английского алфавита, цифр, точек, нижних подчеркиваний и дефисов");
            return;
        }

        //Требования к паролю
        if (!Pattern.matches(Main.PASSWORD_REGEX, password)) {
            showMessage(this, "Требования к паролю:\n" +
                    "1) от 8 до 16 символов\n" +
                    "2) содержать по крайней мере одну букву английского алфавита\n" +
                    "3) содержать по крайней мере одну цифру\n" +
                    "4) состоять из букв английского алфавита, цифр и спец. символов");
            return;
        }

        //Если пользователь с таким логином уже существует, то выдать ошибку
        if (Main.superShop.getUser(login, password).authCode != AuthCode.NO_SUCH_LOGIN) {
            showMessage(this, "Пользователь с указанным логином уже существует!");
            return;
        }

        //Регистрация нового пользователя
        Main.activeUser = new User(login, password, false);
        Main.superShop.addUser(Main.activeUser);

        Main.mainMenuFrame.setLocationRelativeTo(this);
        setVisible(false);
        Main.mainMenuFrame.setVisible(true);
    }

    private void thisComponentShown(ComponentEvent e) {
        tfLogin.setText("");
        pfPassword.setText("");
        pfPasswordAgain.setText("");
        cbShowPassword.setSelected(false);
    }

    private void createUIComponents() {
        tfLogin = new JTextField();
        pfPassword = new JPasswordField();
        pfPasswordAgain = new JPasswordField();

        //Отключать кнопку авторизации если есть пустые поля
        DocumentListener documentListener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                checkEmpty();
            }
            public void removeUpdate(DocumentEvent e) {
                checkEmpty();
            }
            public void insertUpdate(DocumentEvent e) {
                checkEmpty();
            }

            public void checkEmpty() {
                String login = tfLogin.getText();
                String password = String.valueOf(pfPassword.getPassword());
                String passwordAgain = String.valueOf(pfPasswordAgain.getPassword());
                btnRegister.setEnabled(!login.isEmpty() && !password.isEmpty() && !passwordAgain.isEmpty());
            }
        };

        tfLogin.getDocument().addDocumentListener(documentListener);
        pfPassword.getDocument().addDocumentListener(documentListener);
        pfPasswordAgain.getDocument().addDocumentListener(documentListener);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        createUIComponents();

        lblRegister = new JLabel();
        lblLogin = new JLabel();
        lblPassword = new JLabel();
        cbShowPassword = new JCheckBox();
        btnRegister = new JButton();
        lblAuth = new JLabel();
        lblPasswordAgain = new JLabel();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("\u0420\u0435\u0433\u0438\u0441\u0442\u0440\u0430\u0446\u0438\u044f");
        setIconImage(new ImageIcon(getClass().getResource("/res/icon.png")).getImage());
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                thisComponentShown(e);
            }
        });
        Container contentPane = getContentPane();

        //---- lblRegister ----
        lblRegister.setText("\u0420\u0435\u0433\u0438\u0441\u0442\u0440\u0430\u0446\u0438\u044f");
        lblRegister.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        lblRegister.setHorizontalAlignment(SwingConstants.CENTER);

        //---- lblLogin ----
        lblLogin.setText("\u041b\u043e\u0433\u0438\u043d");

        //---- lblPassword ----
        lblPassword.setText("\u041f\u0430\u0440\u043e\u043b\u044c");

        //---- cbShowPassword ----
        cbShowPassword.setText("\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0442\u044c \u043f\u0430\u0440\u043e\u043b\u044c");
        cbShowPassword.addActionListener(e -> cbShowPassword(e));

        //---- btnRegister ----
        btnRegister.setText("\u0417\u0430\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f");
        btnRegister.setEnabled(false);
        btnRegister.addActionListener(e -> btnRegister(e));

        //---- lblAuth ----
        lblAuth.setText("\u0412\u0435\u0440\u043d\u0443\u0442\u044c\u0441\u044f \u043a \u0430\u0432\u0442\u043e\u0440\u0438\u0437\u0430\u0446\u0438\u0438");
        lblAuth.setHorizontalAlignment(SwingConstants.CENTER);
        lblAuth.setForeground(new Color(0x5e87c8));
        lblAuth.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblAuthMouseEntered(e);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lblAuthMouseExited(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                lblAuthMouseReleased(e);
            }
        });

        //---- lblPasswordAgain ----
        lblPasswordAgain.setText("\u041f\u043e\u0432\u0442\u043e\u0440\u0438\u0442\u0435 \u043f\u0430\u0440\u043e\u043b\u044c");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(50, 50, 50)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(lblPasswordAgain)
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(tfLogin, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(pfPassword, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(lblRegister, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(lblLogin)
                                        .addComponent(lblPassword))
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(pfPasswordAgain, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbShowPassword, GroupLayout.Alignment.TRAILING)
                                .addComponent(btnRegister, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(lblAuth, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                            .addGap(50, 50, 50))))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblRegister)
                    .addGap(18, 18, 18)
                    .addComponent(lblLogin)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tfLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(lblPassword)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(pfPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(lblPasswordAgain)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(pfPasswordAgain, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(cbShowPassword)
                    .addGap(40, 40, 40)
                    .addComponent(btnRegister)
                    .addGap(45, 45, 45)
                    .addComponent(lblAuth)
                    .addContainerGap(10, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel lblRegister;
    private JLabel lblLogin;
    private JTextField tfLogin;
    private JLabel lblPassword;
    private JPasswordField pfPassword;
    private JCheckBox cbShowPassword;
    private JButton btnRegister;
    private JLabel lblAuth;
    private JLabel lblPasswordAgain;
    private JPasswordField pfPasswordAgain;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
