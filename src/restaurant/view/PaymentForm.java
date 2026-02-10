package restaurant.view;

import restaurant.model.Order;
import restaurant.repository.OrdersRepository;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;

public class PaymentForm extends JFrame {
    private final Order currentOrder;
    private final boolean isAdmin;

    private final Color colorBg = new Color(253, 252, 240);
    private final Color colorCard = new Color(214, 226, 233);
    private final Color colorCash = new Color(193, 225, 193);
    private final Color colorBack = new Color(255, 183, 178);
    private final Color colorText = new Color(109, 104, 117);

    public PaymentForm(int tableNumber, double total, boolean isAdmin) {
        this.currentOrder = new Order(tableNumber, total);
        this.isAdmin = isAdmin;

        setTitle("Payment - Table " + tableNumber);
        setSize(400, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(colorBg);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel lblTotal = new JLabel("TOTAL: " + String.format("%.2f", total) + " $", SwingConstants.CENTER);
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTotal.setForeground(colorText);
        mainPanel.add(lblTotal, BorderLayout.NORTH);

        JPanel payPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        payPanel.setBackground(colorBg);

        JButton btnCash = createStyledButton("CASH", colorCash);
        JButton btnCard = createStyledButton("CARD", colorCard);

        payPanel.add(btnCash);
        payPanel.add(btnCard);
        mainPanel.add(payPanel, BorderLayout.CENTER);

        JButton btnBack = createStyledButton("BACK", colorBack);
        btnBack.setPreferredSize(new Dimension(0, 45));
        mainPanel.add(btnBack, BorderLayout.SOUTH);

        btnCash.addActionListener(e -> finalizePayment("Cash"));
        btnCard.addActionListener(e -> finalizePayment("Card"));
        btnBack.addActionListener(e -> {
            new OrderForm(tableNumber, isAdmin).setVisible(true);
            this.dispose();
        });

        add(mainPanel);
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(bg);
        btn.setForeground(colorText);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void printReceipt(String method) {
        System.out.println("      SUSHI GARDEN      ");
        System.out.println("------------------------");
        System.out.println("Table: " + currentOrder.getTableNumber());
        System.out.println("Date: " + new Date());
        System.out.println("Method: " + method);
        System.out.println("------------------------");
        System.out.println("TOTAL: " + currentOrder.getTotalAmount() + " $");
        System.out.println("------------------------");
        System.out.println("    THANK YOU!    ");
    }

    private void finalizePayment(String method) {
        OrdersRepository repo = new OrdersRepository();
        boolean success = repo.finalizePayment(currentOrder.getTableNumber(), currentOrder.getTotalAmount());

        if (success) {
            printReceipt(method);
            JOptionPane.showMessageDialog(this, "Payment via " + method + " registered successfully!");
            this.dispose();
            new TablesForm(isAdmin).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Error! No active order found for this table.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}