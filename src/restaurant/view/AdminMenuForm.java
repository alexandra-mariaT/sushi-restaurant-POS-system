package restaurant.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class AdminMenuForm extends JFrame {
    private JPanel adminJPanel;
    private JLabel lblImage;
    private JPanel adminGridLayout;
    private JButton btnLogout;
    private JButton btnDailyClosing;

    public AdminMenuForm() {
        setContentPane(adminJPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        adminGridLayout.setLayout(new GridLayout(1, 2, 2, 2));

        setComponentSizes();
        setupHeaderImage();
        adminJPanel.setBackground(Color.darkGray);
        adminGridLayout.setBackground(Color.darkGray);

        btnDailyClosing.setBackground(Color.green);
        btnDailyClosing.setForeground(Color.white);
        btnDailyClosing.setFont(new Font("Arial", Font.BOLD, 20));

        btnLogout.setBackground(Color.red);
        btnLogout.setForeground(Color.white);
        btnLogout.setFont(new Font("Arial", Font.BOLD, 20));

        btnDailyClosing.addActionListener(e -> openDailyClosing());
        btnLogout.addActionListener(e -> logout());

        setVisible(true);
    }

    private void setComponentSizes() {
        adminGridLayout.setPreferredSize(new Dimension(500, 300));
        Dimension bigButtonSize = new Dimension(75, 75);
        btnDailyClosing.setPreferredSize(bigButtonSize);
        btnLogout.setPreferredSize(bigButtonSize);

        lblImage.setPreferredSize(new Dimension(500, 300));
    }

    private void setupHeaderImage() {
        String imagePath = "images/sushi.jpg";
        URL imgURL = AdminMenuForm.class.getClassLoader().getResource(imagePath);
        Image originalImage = null;

        try {
            assert imgURL != null;
            originalImage = ImageIO.read(imgURL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Image scaledImage = originalImage.getScaledInstance(500, 300, Image.SCALE_SMOOTH);
        lblImage.setIcon(new ImageIcon(scaledImage));
        lblImage.setText("");
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void openDailyClosing() {
        JOptionPane.showMessageDialog(this, "Are you sure you want to generate the Z report?");
        new DailyClosingForm().setVisible(true);
    }

    private void logout() {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            new LoginForm().setVisible(true);
            this.dispose();
        }
    }
}