package restaurant.repository;

import restaurant.database.DatabaseConnection;
import restaurant.model.MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuItemsRepository {
    public List<MenuItem> getAllMenuItems() {

        String sql = "SELECT m.*, c.IsFood " +
                "FROM MenuItems m " +
                "JOIN Categories c ON m.CategoryID = c.CategoryID " +
                "ORDER BY m.CategoryID, m.Name";

        List<MenuItem> menu = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int menuItemId = rs.getInt("MenuItemID");
                String name = rs.getString("Name");
                double price = rs.getDouble("Price");
                String ingredients = rs.getString("Ingredients");
                String imageFile = rs.getString("ImageFile");
                int categoryId = rs.getInt("CategoryID");
                boolean isFood = rs.getBoolean("IsFood");

                MenuItem item = new MenuItem(menuItemId, name, price, ingredients, imageFile, categoryId);
                menu.add(item);
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return menu;
    }
}