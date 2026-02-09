package restaurant.repository;

import restaurant.database.DatabaseConnection;
import restaurant.model.Admin;
import restaurant.model.Waiter;
import restaurant.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public Person findUserByPin(String pinCode) {
        String query = "SELECT UserName, UserRole FROM Users WHERE PinCode = ?";
        Person person = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, pinCode);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("UserName");
                String role = rs.getString("UserRole");

                if (role.equals("Admin")) {
                    person = new Admin(name);
                } else if (role.equals("Ospatar")) {
                    person = new Waiter(name);
                } else {
                    System.err.println("Cod invalid!");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return person;
    }
}