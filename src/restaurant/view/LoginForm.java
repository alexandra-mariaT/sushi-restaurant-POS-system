package restaurant.view;

import restaurant.controller.UserController;
import restaurant.model.Admin;
import restaurant.model.Person;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginForm extends JFrame {
    private final JPasswordField pFieldLogin;
    private final StringBuilder password = new StringBuilder();
    private final UserController userController = new UserController();

    private final Color colorBg = new Color(253, 252, 240);
    private final Color colorBtn = new Color(214, 226, 233);
    private final Color colorOK = new Color(193, 225, 193);
    private final Color colorCancel = new Color(255, 183, 178);
    private final Color colorText = new Color(109, 104, 117);

    public LoginForm() {
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(colorBg);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        headerPanel.setBackground(colorBg);

        JLabel lblTitle = new JLabel("ENTER PIN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(colorText);

        pFieldLogin = new JPasswordField();
        pFieldLogin.setEditable(false);
        pFieldLogin.setHorizontalAlignment(JTextField.CENTER);
        pFieldLogin.setFont(new Font("Arial", Font.BOLD, 24));
        pFieldLogin.setBorder(BorderFactory.createLineBorder(colorBtn, 2));

        headerPanel.add(lblTitle);
        headerPanel.add(pFieldLogin);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel keypadPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        keypadPanel.setBackground(colorBg);

        for (int i = 1; i <= 9; i++) {
            keypadPanel.add(createNumButton(String.valueOf(i)));
        }

        JButton btnCancel = createNumButton("C");
        btnCancel.setBackground(colorCancel);
        btnCancel.addActionListener(e -> clearPassword());

        JButton btn0 = createNumButton("0");

        JButton btnLogin = createNumButton("OK");
        btnLogin.setBackground(colorOK);
        btnLogin.addActionListener(e -> attemptLogin());

        keypadPanel.add(btnCancel);
        keypadPanel.add(btn0);
        keypadPanel.add(btnLogin);

        mainPanel.add(keypadPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JButton createNumButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setForeground(colorText);
        btn.setBackground(colorBtn);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        if (text.matches("\\d")) btn.addActionListener(e -> addDigit(text));
        return btn;
    }

    private void addDigit(String cifra) {
        if (password.length() < 4) {
            password.append(cifra);
            pFieldLogin.setText("*".repeat(password.length()));
        }
    }

    private void clearPassword() {
        password.setLength(0);
        pFieldLogin.setText("");
    }

    private void attemptLogin() {
        Person person = userController.login(password.toString());
        if (person != null) {
            boolean isAdmin = (person instanceof Admin);
            new TablesForm(isAdmin).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid PIN!", "Error", JOptionPane.ERROR_MESSAGE);
            clearPassword();
        }
    }
}