/*
 * Created by JFormDesigner on Wed Mar 20 21:17:54 QYZT 2024
 */

package com.lab6.frames;

import java.beans.*;
import javax.swing.event.*;
import com.lab6.*;
import javafx.scene.input.KeyCode;

import static com.lab6.Main.showMessage;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Сергей
 */
public class LoginFrame extends JFrame {
    private final Color LINK_DEFAULT_COLOR = new Color(0x5E87C8);
    private final Color LINK_HOVER_COLOR = new Color(0x82A2D4);

    private char defaultEchoChar;

    public LoginFrame() {
        initComponents();

        defaultEchoChar = pfPassword.getEchoChar();
    }

    //Показать или скрыть пароль при клике на чекбокс "Показать пароль"
    private void cbShowPassword(ActionEvent e) {
        if (cbShowPassword.isSelected()) {
            pfPassword.setEchoChar((char) 0);
        } else {
            pfPassword.setEchoChar(defaultEchoChar);
        }
    }

    private void lblRegisterMouseEntered(MouseEvent e) {
        lblRegister.setForeground(LINK_HOVER_COLOR);
    }

    private void lblRegisterMouseExited(MouseEvent e) {
        lblRegister.setForeground(LINK_DEFAULT_COLOR);
    }

    //При нажатии на лейбл "Зарегистрируйтесь сейчас", открыть окно регистрации
    private void lblRegisterMouseReleased(MouseEvent e) {
        Main.registerFrame.setLocationRelativeTo(this);
        setVisible(false);
        Main.registerFrame.setVisible(true);
    }

    //Нажатие на кнопку "Войти", попытка войти в систему
    private void btnAuth(ActionEvent e) {
        String login = tfLogin.getText();
        String password = String.valueOf(pfPassword.getPassword());

        //Попытка войти
        AuthResult authResult = Main.superShop.getUser(login, password);

        //Проверка результата поиска пользователя. Если всё ОК - вернуть этого пользователя
        switch (authResult.authCode) {
            case NO_SUCH_LOGIN: {
                showMessage(this, "Пользователя с таким логином не найдено!");
                break;
            }

            case WRONG_PASSWORD: {
                showMessage(this, "Введён неверный пароль!");
                break;
            }

            case SUCCESS: {
                Main.activeUser = authResult.user;

                Main.mainMenuFrame.setLocationRelativeTo(this);
                setVisible(false);
                Main.mainMenuFrame.setVisible(true);
            }
        }
    }

    private void thisComponentShown(ComponentEvent e) {
        tfLogin.setText("");
        pfPassword.setText("");
        cbShowPassword.setSelected(false);
    }

    private void createUIComponents() {
        tfLogin = new JTextField();
        pfPassword = new JPasswordField();

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
                btnAuth.setEnabled(!login.isEmpty() && !password.isEmpty());
            }
        };

        tfLogin.getDocument().addDocumentListener(documentListener);
        pfPassword.getDocument().addDocumentListener(documentListener);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        createUIComponents();

        lblAuth = new JLabel();
        lblLogin = new JLabel();
        lblPassword = new JLabel();
        cbShowPassword = new JCheckBox();
        btnAuth = new JButton();
        lblRegister = new JLabel();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("\u0410\u0432\u0442\u043e\u0440\u0438\u0437\u0430\u0446\u0438\u044f");
        setIconImage(new ImageIcon(getClass().getResource("/res/icon.png")).getImage());
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                thisComponentShown(e);
            }
        });
        Container contentPane = getContentPane();

        //---- lblAuth ----
        lblAuth.setText("\u0410\u0432\u0442\u043e\u0440\u0438\u0437\u0430\u0446\u0438\u044f");
        lblAuth.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        lblAuth.setHorizontalAlignment(SwingConstants.CENTER);

        //---- lblLogin ----
        lblLogin.setText("\u041b\u043e\u0433\u0438\u043d");

        //---- lblPassword ----
        lblPassword.setText("\u041f\u0430\u0440\u043e\u043b\u044c");

        //---- cbShowPassword ----
        cbShowPassword.setText("\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0442\u044c \u043f\u0430\u0440\u043e\u043b\u044c");
        cbShowPassword.addActionListener(e -> cbShowPassword(e));

        //---- btnAuth ----
        btnAuth.setText("\u0412\u043e\u0439\u0442\u0438");
        btnAuth.setEnabled(false);
        btnAuth.addActionListener(e -> btnAuth(e));

        //---- lblRegister ----
        lblRegister.setText("\u0417\u0430\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0438\u0440\u0443\u0439\u0442\u0435\u0441\u044c \u0441\u0435\u0439\u0447\u0430\u0441");
        lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
        lblRegister.setForeground(new Color(0x5e87c8));
        lblRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblRegisterMouseEntered(e);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lblRegisterMouseExited(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                lblRegisterMouseReleased(e);
            }
        });

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addGap(50, 50, 50)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(tfLogin, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                        .addComponent(pfPassword, GroupLayout.Alignment.LEADING)
                        .addComponent(lblAuth, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(0, 97, Short.MAX_VALUE)
                            .addComponent(cbShowPassword))
                        .addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(lblLogin)
                                .addComponent(lblPassword))
                            .addGap(0, 186, Short.MAX_VALUE))
                        .addComponent(btnAuth, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                        .addComponent(lblRegister, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                    .addGap(50, 50, 50))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblAuth)
                    .addGap(18, 18, 18)
                    .addComponent(lblLogin)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tfLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(lblPassword)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(pfPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(cbShowPassword)
                    .addGap(40, 40, 40)
                    .addComponent(btnAuth)
                    .addGap(45, 45, 45)
                    .addComponent(lblRegister)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel lblAuth;
    private JLabel lblLogin;
    private JTextField tfLogin;
    private JLabel lblPassword;
    private JPasswordField pfPassword;
    private JCheckBox cbShowPassword;
    private JButton btnAuth;
    private JLabel lblRegister;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
