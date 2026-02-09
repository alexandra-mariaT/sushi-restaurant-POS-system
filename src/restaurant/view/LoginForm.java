package restaurant.view;

import restaurant.controller.UserController;
import restaurant.model.Admin;
import restaurant.model.Waiter;
import restaurant.model.Person;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {
    private JPanel loginJPanel;
    private JPasswordField pFieldLogin;
    private JButton btn0;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JButton btn5;
    private JButton btn6;
    private JButton btn7;
    private JButton btn8;
    private JButton btn9;
    private JButton btnCancel;
    private JButton btnLogin;
    private JPanel keypadJPanel;
    private final StringBuilder password = new StringBuilder();
    private final UserController userController;

    public LoginForm() {
        setContentPane(loginJPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 350);
        setLocationRelativeTo(null);

        this.userController = new UserController();
        GridLayout keypadLayout = new GridLayout(4, 3); //ca sa fie egale butoanele le am pus intr un grid layout
        keypadJPanel.setLayout(keypadLayout);

        applyStyle();

        btn0.addActionListener(e -> addCifra(0));
        btn1.addActionListener(e -> addCifra(1));
        btn2.addActionListener(e -> addCifra(2));
        btn3.addActionListener(e -> addCifra(3));
        btn4.addActionListener(e -> addCifra(4));
        btn5.addActionListener(e -> addCifra(5));
        btn6.addActionListener(e -> addCifra(6));
        btn7.addActionListener(e -> addCifra(7));
        btn8.addActionListener(e -> addCifra(8));
        btn9.addActionListener(e -> addCifra(9));
        btnCancel.addActionListener(e -> clearPassword());
        btnLogin.addActionListener(e -> attemptLogin());

        setVisible(true);
    }

    private void applyStyle() {
        loginJPanel.setBackground(Color.darkGray);

        JButton[] buttons = {btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
        for (JButton btn : buttons) {
            btn.setBackground(Color.darkGray);
            btn.setForeground(Color.white);
            btn.setFont(new Font("Arial", Font.BOLD, 20));
        }

        btnLogin.setBackground(Color.green);
        btnLogin.setForeground(Color.white);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 20));

        btnCancel.setBackground(Color.red);
        btnCancel.setForeground(Color.white);
        btnCancel.setFont(new Font("Arial", Font.BOLD, 20));
    }
    private void addCifra(int cifra) {
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
        String enteredPin = password.toString();
        Person person = userController.login(enteredPin);

        if (person != null) {
            if (person instanceof Admin) {
                System.out.println("Admin: " + person.getUserName());
                new AdminMenuForm().setVisible(true);
            } else if (person instanceof Waiter) {
                System.out.println("Waiter: " + person.getUserName());
                new TablesForm().setVisible(true);
            }

            this.dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Error: Invalid pin!");
        }

        clearPassword();
    }
}