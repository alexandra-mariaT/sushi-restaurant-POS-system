package restaurant.view;

import restaurant.model.Order;

import javax.swing.*;
import java.awt.*;

public class PaymentForm extends JFrame {
    private final JPanel mainPanel;
    private final JPanel headerPanel;
    private final JPanel paymentOptionsPanel;
    private final JPanel southPanel;
    private final JButton btnCash;
    private final JButton btnCard;
    private final JButton btnBack;
    private final JButton btnLogout;
    private final JLabel lblTotalAmount;
    private final Order currentOrder;

    public PaymentForm(int tableNumber, double total) {
        this.currentOrder = new Order(tableNumber, total);
        mainPanel = new JPanel();
        headerPanel = new JPanel();
        paymentOptionsPanel = new JPanel();
        southPanel = new JPanel();

        btnCash = new JButton("Cash");
        btnCard = new JButton("Card");
        btnBack = new JButton("Back");
        btnLogout = new JButton("Logout");
        lblTotalAmount = new JLabel();

        setContentPane(mainPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setTitle(("Table: " + currentOrder.getTableNumber()));

        mainPanel.setLayout(new BorderLayout());
        headerPanel.setLayout(new GridLayout(2, 1, 5, 5));
        paymentOptionsPanel.setLayout(new GridLayout(1, 2, 10, 10));
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        headerPanel.add(lblTotalAmount);

        paymentOptionsPanel.add(btnCash);
        paymentOptionsPanel.add(btnCard);

        southPanel.add(btnBack);
        southPanel.add(btnLogout);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(paymentOptionsPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        displayOrderDetails();

        applyStyling();

        btnCash.addActionListener(e -> finalizePayment("Cash"));
        btnCard.addActionListener(e -> finalizePayment("Card"));

        btnBack.addActionListener(e -> goBackToOrderForm());
        btnLogout.addActionListener(e -> logout());

        setVisible(true);
    }

    private void displayOrderDetails() {
        lblTotalAmount.setText(String.format("TOTAL: %.2f RON", currentOrder.getTotalAmount()));
        lblTotalAmount.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void applyStyling() {
        mainPanel.setBackground(Color.blue);
        headerPanel.setBackground(Color.darkGray);
        paymentOptionsPanel.setBackground(Color.darkGray);
        southPanel.setBackground(Color.darkGray);

        lblTotalAmount.setForeground(Color.white);
        lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.setPreferredSize(new Dimension(350, 50));

        btnCash.setBackground(new Color(122, 150, 54));
        btnCash.setForeground(Color.white);
        btnCash.setFont(new Font("Arial", Font.BOLD, 20));

        btnCard.setBackground(new Color(40, 63, 120));
        btnCard.setForeground(Color.white);
        btnCard.setFont(new Font("Arial", Font.BOLD, 20));

        btnLogout.setBackground(Color.red);
        btnLogout.setForeground(Color.white);
        btnLogout.setFont(new Font("Arial", Font.BOLD, 20));

        btnBack.setBackground(Color.darkGray);
        btnBack.setForeground(Color.white);
        btnBack.setFont(new Font("Arial", Font.BOLD, 20));

        Dimension paymentButtonSize = new Dimension(75, 40);
        btnCash.setPreferredSize(paymentButtonSize);
        btnCard.setPreferredSize(paymentButtonSize);

        Dimension navButtonSize = new Dimension(110, 40);
        btnBack.setPreferredSize(navButtonSize);
        btnLogout.setPreferredSize(navButtonSize);
    }

    private void finalizePayment(String method) {
        JOptionPane.showMessageDialog(this, "Successful payment! Table " + currentOrder.getTableNumber() + " closed.");
        this.dispose();
        new TablesForm().setVisible(true);
    }

    private void goBackToOrderForm() {
        new OrderForm(currentOrder.getTableNumber()).setVisible(true);
        this.dispose();
    }

    private void logout() {
        int result = JOptionPane.showConfirmDialog(this, "The current order will be lost. Do you want to continue?", "", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            new LoginForm().setVisible(true);
            this.dispose();
        }
    }
}