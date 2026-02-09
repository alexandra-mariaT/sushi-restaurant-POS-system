package restaurant.view;

import restaurant.controller.MenuController;
import restaurant.model.MenuItem;
import restaurant.model.Order;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/// ar trebui sa stochez comenzile ca sa nu se piarda
public class OrderForm extends JFrame {
    private final int tableNumber;
    private JPanel orderPanel;
    private JPanel southPanel;
    private JButton btnBack;
    private JButton btnCheckout;
    private JPanel menuItemsPanel;
    private final MenuController menuController;
    private final List<Integer> currentOrderItems = new ArrayList<>();
    private final Map<Integer, Double> menuPrices = new HashMap<>();

    public OrderForm(int tableNumber) {
        this.tableNumber = tableNumber;
        this.menuController = new MenuController();

        initializeComponents();
        loadMenu();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);
        applyStyle();
        btnBack.addActionListener(e -> goBackOrLogout());
        btnCheckout.addActionListener(e -> goToCheckOut());
        setContentPane(orderPanel);
        setVisible(true);
    }

    private void initializeComponents() {
        orderPanel = new JPanel(new BorderLayout());
        menuItemsPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(menuItemsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        orderPanel.add(scrollPane, BorderLayout.CENTER);
        southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        btnBack = new JButton("Back");
        btnCheckout = new JButton("Checkout");

        southPanel.add(btnBack);
        southPanel.add(btnCheckout);
        orderPanel.add(southPanel, BorderLayout.SOUTH);
    }

    private void loadMenu() {
        List<MenuItem> menu = menuController.getFullMenu();
        menuItemsPanel.setLayout(new GridLayout(menu.size()/3, 3, 10, 10));

        for (MenuItem item : menu) {
            menuPrices.put(item.getMenuItemId(), item.getPrice());
            addMenuItem(item.getMenuItemId(), item.getName(), item.getPrice(), item.getImageFile());
        }
    }

    private void addMenuItem(int menuItemId, String name, double price, String imageFile) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setBackground(new Color(92, 69, 69));
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));

        Dimension itemDimension = new Dimension(120, 180);
        itemPanel.setPreferredSize(itemDimension);
        itemPanel.setMaximumSize(itemDimension);
        itemPanel.setMinimumSize(itemDimension);

        JLabel lblImage = new JLabel();
        loadImage(lblImage, "images/" + imageFile);
        lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblName = new JLabel(name);
        JLabel lblPrice = new JLabel(String.format(" %.2f RON", price));
        lblName.setForeground(Color.black);
        lblPrice.setForeground(Color.black);
        lblName.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPrice.setFont(new Font("Arial", Font.BOLD, 14));

        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPrice.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonRow = new JPanel(new GridLayout(1, 2, 5, 0));
        buttonRow.setBackground(itemPanel.getBackground());

        JButton btnAdd = new JButton("+");
        btnAdd.addActionListener(e -> addToOrder(menuItemId, name));
        btnAdd.setBackground(Color.green);
        btnAdd.setForeground(Color.white);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 20));

        JButton btnRemove = new JButton("-");
        btnRemove.addActionListener(e -> removeFromOrder(menuItemId, name));
        btnRemove.setBackground(Color.red);
        btnRemove.setForeground(Color.white);
        btnRemove.setFont(new Font("Arial", Font.BOLD, 20));

        buttonRow.add(btnAdd);
        buttonRow.add(btnRemove);

        itemPanel.add(lblImage);
        itemPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        itemPanel.add(lblName);
        itemPanel.add(lblPrice);
        itemPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        itemPanel.add(buttonRow);
        itemPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        menuItemsPanel.add(itemPanel);
    }

    private void addToOrder(int menuItemId, String name) {
        currentOrderItems.add(menuItemId);
        System.out.println(name + " was added to the order.");
    }

    private void removeFromOrder(int menuItemId, String name) {
        boolean removed = currentOrderItems.remove((Integer) menuItemId);

        if (removed) {
            System.out.println(name + " was removed from order.");
        } else {
            System.out.println(name + " was not ordered and cannot be removed.");
        }
    }

    private double calculateOrderTotal() {
        double total = 0.0;
        for (int itemId : currentOrderItems) {
            if (menuPrices.containsKey(itemId)) {
                total += menuPrices.get(itemId);
            }
        }
        return total;
    }

    private void goToCheckOut() {
        double total = calculateOrderTotal();

        if (total <= 0) {
            JOptionPane.showMessageDialog(this, "The order is empty!");
            return;
        }

        Order order = new Order(tableNumber, total);
        System.out.println("Total: " + total);
        new PaymentForm(tableNumber, total).setVisible(true);

        this.dispose();
    }

    private void loadImage(JLabel label, String imagePath) {
        URL imgURL = OrderForm.class.getClassLoader().getResource(imagePath);
        Image originalImage = Toolkit.getDefaultToolkit().getImage(imgURL);

        MediaTracker tracker = new MediaTracker(this);
        tracker.addImage(originalImage, 0);

        try {
            tracker.waitForID(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(scaledImage));
        label.setText("");

    }

    private void applyStyle() {
        orderPanel.setBackground(Color.darkGray);
        southPanel.setBackground(Color.darkGray);
        menuItemsPanel.setBackground(Color.darkGray);

        btnCheckout.setBackground(Color.green);
        btnCheckout.setForeground(Color.white);
        btnCheckout.setFont(new Font("Arial", Font.BOLD, 20));
        btnCheckout.setPreferredSize(new Dimension(180, 40));

        btnBack.setBackground(Color.red);
        btnBack.setForeground(Color.white);
        btnBack.setFont(new Font("Arial", Font.BOLD, 20));
        btnBack.setPreferredSize(new Dimension(180, 40));
    }

    private void goBackOrLogout() {
        int result = JOptionPane.showConfirmDialog(this, "The current order will be lost. Do you want to continue?", "", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            new LoginForm().setVisible(true);
            this.dispose();
        }
    }
}