package restaurant.view;

import javax.swing.*;
import java.awt.*;

public class TablesForm extends JFrame {
    private JPanel tablesPanel;
    private JButton btnLogout;
    private JButton btnTable1;
    private JButton btnTable2;
    private JButton btnTable3;
    private JButton btnTable4;
    private JPanel mainPanel;

    public TablesForm() {
        setContentPane(tablesPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        initializeComponents();
        applyStyling();

        btnTable1.addActionListener(e -> openOrderForTable(1));
        btnTable2.addActionListener(e -> openOrderForTable(2));
        btnTable3.addActionListener(e -> openOrderForTable(3));
        btnTable4.addActionListener(e -> openOrderForTable(4));
        btnLogout.addActionListener(e -> logout());

        setVisible(true);
    }

    private void initializeComponents() {
        tablesPanel.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 2, 5, 5));

        mainPanel.add(btnTable1);
        mainPanel.add(btnTable2);
        mainPanel.add(btnTable3);
        mainPanel.add(btnTable4);

        btnTable1.setText("Table 1");
        btnTable2.setText("Table 2");
        btnTable3.setText("Table 3");
        btnTable4.setText("Table 4");
        btnLogout.setText("Logout");

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        southPanel.setBackground(Color.darkGray);
        southPanel.add(btnLogout);

        tablesPanel.add(mainPanel, BorderLayout.CENTER);
        tablesPanel.add(southPanel, BorderLayout.SOUTH);
    }

    private void applyStyling() {
        btnStyle(btnTable1);
        btnStyle(btnTable2);
        btnStyle(btnTable3);
        btnStyle(btnTable4);

        btnLogout.setBackground(Color.red);
        btnLogout.setFont(new Font("Arial", Font.BOLD, 20));
        btnLogout.setForeground(Color.white);

        btnLogout.setPreferredSize(new Dimension(120, 40));
    }

    private void btnStyle(JButton btn) {
        btn.setBackground(new Color(179, 118, 129));
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setForeground(Color.black);
    }

    private void openOrderForTable(int tableNumber) {
        System.out.println("Table " + tableNumber + " order:");
        new OrderForm(tableNumber).setVisible(true);
        this.dispose();
    }

    private void logout() {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            new LoginForm().setVisible(true);
            this.dispose();
        }
    }
}