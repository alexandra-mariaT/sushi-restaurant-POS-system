package restaurant.view;

import restaurant.controller.MenuController;
import restaurant.model.MenuItem;
import restaurant.repository.OrdersRepository;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderForm extends JFrame {
    private final int tableNumber;
    private final boolean isAdmin;
    private final MenuController menuController;
    private final List<Integer> currentOrderItems = new ArrayList<>();
    private final Map<Integer, Double> menuPrices = new HashMap<>();
    private double totalAmount = 0.0;

    private JPanel menuItemsPanel;
    private JLabel lblTotal;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    private final Color colorBg = new Color(253, 252, 240);
    private final Color colorSidebar = new Color(214, 226, 233);
    private final Color colorCancel = new Color(255, 183, 178);

    public OrderForm(int tableNumber, boolean isAdmin) {
        this.tableNumber = tableNumber;
        this.isAdmin = isAdmin;
        this.menuController = new MenuController();

        OrdersRepository repo = new OrdersRepository();
        this.totalAmount = repo.getActiveOrderTotal(tableNumber);

        setTitle("Table " + tableNumber);
        setSize(1000, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
        loadMenu();

        if (totalAmount > 0)
            listModel.addElement("Amount: " + totalAmount + " $");

        setVisible(true);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(colorBg);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        menuItemsPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        menuItemsPanel.setBackground(colorBg);
        mainPanel.add(new JScrollPane(menuItemsPanel), BorderLayout.CENTER);

        JPanel sidebar = new JPanel(new BorderLayout(10, 10));
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBackground(colorSidebar);
        sidebar.setBorder(new EmptyBorder(15, 15, 15, 15));

        sidebar.add(new JLabel("CURRENT ORDER", SwingConstants.CENTER), BorderLayout.NORTH);
        sidebar.add(new JScrollPane(new JList<>(listModel)), BorderLayout.CENTER);

        JPanel footerSidebar = new JPanel(new GridLayout(4, 1, 5, 5));
        footerSidebar.setOpaque(false);

        lblTotal = new JLabel("TOTAL: " + String.format("%.2f", totalAmount) + " $", SwingConstants.RIGHT);
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JButton btnSave = new JButton("SEND ORDER");
        styleBtn(btnSave, new Color(193, 225, 193));
        btnSave.addActionListener(e -> saveAction("Pending"));

        JButton btnPay = new JButton("GO TO PAYMENT");
        styleBtn(btnPay, new Color(162, 210, 255));
        btnPay.addActionListener(e -> saveAction("Closed"));

        JButton btnCancel = new JButton("CANCEL ORDER");
        styleBtn(btnCancel, colorCancel);
        btnCancel.addActionListener(e -> cancelAction());

        footerSidebar.add(lblTotal);
        footerSidebar.add(btnSave);
        footerSidebar.add(btnPay);
        footerSidebar.add(btnCancel);

        sidebar.add(footerSidebar, BorderLayout.SOUTH);

        mainPanel.add(sidebar, BorderLayout.EAST);
        add(mainPanel);
    }

    private void loadMenu() {
        List<MenuItem> menu = menuController.getFullMenu();
        for (MenuItem item : menu) {
            menuPrices.put(item.getMenuItemId(), item.getPrice());

            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel lblImg = new JLabel();
            lblImg.setAlignmentX(0.5f);
            loadImage(lblImg, "images/" + item.getImageFile());

            JLabel lblName = new JLabel(item.getName());
            lblName.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblName.setAlignmentX(0.5f);

            JButton btnAdd = new JButton("+ " + item.getPrice() + " $");
            btnAdd.setAlignmentX(0.5f);
            btnAdd.setFocusPainted(false);
            btnAdd.setBackground(new Color(240, 240, 240));
            btnAdd.addActionListener(e -> {
                totalAmount += item.getPrice();
                listModel.addElement(item.getName());
                lblTotal.setText(String.format("TOTAL: %.2f $", totalAmount));
            });

            card.add(lblImg);
            card.add(Box.createVerticalStrut(10));
            card.add(lblName);
            card.add(Box.createVerticalStrut(10));
            card.add(btnAdd);
            menuItemsPanel.add(card);
        }
    }

    private void saveAction(String status) {
        if (totalAmount <= 0) {
            JOptionPane.showMessageDialog(this, "Add items first!");
            return;
        }

        OrdersRepository repo = new OrdersRepository();
        boolean success;

        double existing = repo.getActiveOrderTotal(tableNumber);
        if (existing > 0) {
            success = repo.updateOrderAmount(tableNumber, totalAmount);
        } else {
            success = repo.insertOrder(tableNumber, totalAmount, "Pending");
        }

        if (success) {
            if (status.equals("Pending")) {
                this.dispose();
                new TablesForm(isAdmin).setVisible(true);
            } else {
                new PaymentForm(tableNumber, totalAmount, isAdmin).setVisible(true);
                this.dispose();
            }
        }
    }

    private void cancelAction() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel the order?",
                "Cancel Order", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            OrdersRepository repo = new OrdersRepository();
            repo.cancelOrder(tableNumber);

            this.dispose();
            new TablesForm(isAdmin).setVisible(true);
        }
    }

    private void styleBtn(JButton b, Color c) {
        b.setBackground(c);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
    }

    private void loadImage(JLabel label, String path) {
        try {
            URL url = getClass().getClassLoader().getResource(path);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(img));
            } else {
                label.setText("No Image");
            }
        } catch (Exception e) { label.setText("Image Error"); }
    }
}