package restaurant.view;

import restaurant.repository.OrdersRepository;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TablesForm extends JFrame {
    private final boolean isAdmin;
    private final Color colorBg = new Color(253, 252, 240);
    private final Color colorFree = new Color(193, 225, 193);
    private final Color colorOccupied = new Color(255, 183, 178);
    private final Color colorNav = new Color(109, 104, 117);

    public TablesForm(boolean isAdmin) {
        this.isAdmin = isAdmin;
        setTitle("Restaurant Tables");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(25, 25));
        mainPanel.setBackground(colorBg);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel lblTitle = new JLabel("SUSHI GARDEN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(colorNav);
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(3, 4, 20, 20));
        gridPanel.setBackground(colorBg);

        OrdersRepository repo = new OrdersRepository();
        boolean isDayClosed = repo.isDayAlreadyClosed();

        for (int i = 1; i <= 12; i++) {
            int tableNum = i;
            JButton btn = new JButton();
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btn.setFocusPainted(false);

            double activeTotal = repo.getActiveOrderTotal(tableNum);
            if (activeTotal > 0) {
                btn.setBackground(colorOccupied);
                btn.setText("Table " + tableNum);
            } else {
                btn.setBackground(colorFree);
                btn.setText("Table " + tableNum);
            }

            btn.addActionListener(e -> {
                if (isDayClosed) {
                    JOptionPane.showMessageDialog(this, "Day is closed. No new orders allowed.");
                } else {
                    new OrderForm(tableNum, isAdmin).setVisible(true);
                    this.dispose();
                }
            });
            gridPanel.add(btn);
        }
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        footer.setBackground(colorBg);

        if (isAdmin) {
            JButton btnZ = new JButton("Daily Report (Z)");
            styleFooterBtn(btnZ);
            btnZ.addActionListener(e -> new DailyClosingForm(isAdmin).setVisible(true));
            footer.add(btnZ);
        }

        JButton btnLogout = new JButton("Logout");
        styleFooterBtn(btnLogout);
        btnLogout.addActionListener(e -> {
            new LoginForm().setVisible(true);
            this.dispose();
        });
        footer.add(btnLogout);

        mainPanel.add(footer, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void styleFooterBtn(JButton btn) {
        btn.setPreferredSize(new Dimension(150, 45));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
    }
}