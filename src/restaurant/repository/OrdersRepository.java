package restaurant.repository;

import restaurant.database.DatabaseConnection;
import java.sql.*;

public class OrdersRepository {

    public double getActiveOrderTotal(int tableNumber) {
        String sql = "SELECT TotalAmount FROM Orders WHERE TableNumber = ? AND Status = 'Pending'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tableNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("TotalAmount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public boolean updateOrderAmount(int tableNumber, double newTotal) {
        String sql = "UPDATE Orders SET TotalAmount = ? WHERE TableNumber = ? AND Status = 'Pending'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newTotal);
            pstmt.setInt(2, tableNumber);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertOrder(int tableNumber, double total, String status) {
        String sql = "INSERT INTO Orders (TableNumber, TotalAmount, OrderDate, Status) VALUES (?, ?, GETDATE(), ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tableNumber);
            pstmt.setDouble(2, total);
            pstmt.setString(3, status);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isDayAlreadyClosed() {
        String sql = "SELECT COUNT(*) FROM DailyClosing WHERE ClosingDate = CAST(GETDATE() AS DATE)";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getActiveOrdersCount() {
        String sql = "SELECT COUNT(*) FROM Orders WHERE Status = 'Pending'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ClosingStats getTodayStats() {
        String sql = "SELECT SUM(TotalAmount) as Revenue, COUNT(OrderID) as OrderCount " +
                "FROM Orders WHERE CAST(OrderDate AS DATE) = CAST(GETDATE() AS DATE) " +
                "AND Status = 'Closed'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return new ClosingStats(rs.getDouble("Revenue"), rs.getInt("OrderCount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ClosingStats(0, 0);
    }

    public boolean saveDailyClosing(double totalRevenue, int totalOrders, int userId) {
        String sql = "INSERT INTO DailyClosing (ClosingDate, TotalRevenue, TotalOrders, ClosedByUserID) VALUES (CAST(GETDATE() AS DATE), ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, totalRevenue);
            pstmt.setInt(2, totalOrders);
            pstmt.setInt(3, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean cancelOrder(int tableNumber) {
        String sql = "DELETE FROM Orders WHERE TableNumber = ? AND Status = 'Pending'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tableNumber);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean finalizePayment(int tableNumber, double finalAmount) {
        String sql = "UPDATE Orders SET Status = 'Closed', TotalAmount = ? " +
                "WHERE TableNumber = ? AND Status = 'Pending'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, finalAmount);
            pstmt.setInt(2, tableNumber);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static class ClosingStats {
        public double revenue;
        public int count;

        public ClosingStats(double revenue, int count) {
            this.revenue = revenue;
            this.count = count;
        }
    }
}