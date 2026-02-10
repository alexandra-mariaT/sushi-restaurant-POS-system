package restaurant.view;

import restaurant.repository.OrdersRepository;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyClosingForm extends JFrame {
    private final OrdersRepository repo = new OrdersRepository();
    private final boolean isAdmin;
    private final Color colorBg = new Color(253, 252, 240);
    private final Color colorText = new Color(109, 104, 117);

    public DailyClosingForm(boolean isAdmin) {
        this.isAdmin = isAdmin;
        setTitle("Z Report");
        setSize(320, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        mainPanel.setBackground(colorBg);
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        OrdersRepository.ClosingStats stats = repo.getTodayStats();

        JLabel lblHeader = new JLabel("Z REPORT", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHeader.setForeground(colorText);

        JLabel lblDate = new JLabel("Date: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()), SwingConstants.CENTER);
        JLabel lblRev = new JLabel("REVENUE: " + String.format("%.2f", stats.revenue) + " RON", SwingConstants.CENTER);
        lblRev.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel lblCount = new JLabel("Orders: " + stats.count, SwingConstants.CENTER);

        JButton btnZ = new JButton("GENERATE Z");
        btnZ.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnZ.setBackground(new Color(255, 183, 178));
        btnZ.setFocusPainted(false);

        if (repo.isDayAlreadyClosed()) {
            btnZ.setEnabled(false);
            btnZ.setText("CLOSED");
        }

        btnZ.addActionListener(e -> {
            if (repo.getActiveOrdersCount() > 0) {
                JOptionPane.showMessageDialog(this, "Error: Active tables exist.");
                return;
            }
            if (repo.saveDailyClosing(stats.revenue, stats.count, 1)) {
                this.dispose();
                new TablesForm(isAdmin).setVisible(true);
            }
        });

        mainPanel.add(lblHeader);
        mainPanel.add(lblDate);
        mainPanel.add(new JSeparator());
        mainPanel.add(lblRev);
        mainPanel.add(lblCount);
        mainPanel.add(btnZ);

        add(mainPanel);
    }
}