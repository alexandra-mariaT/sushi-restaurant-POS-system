package restaurant.main;

public class Main {
    public static void main(String[] args) {
        restaurant.database.DatabaseMigration.runMigrations();

        javax.swing.SwingUtilities.invokeLater(() -> {
            new restaurant.view.LoginForm().setVisible(true);
        });
    }
}