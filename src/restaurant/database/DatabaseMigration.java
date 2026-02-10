package restaurant.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseMigration {

    public static void runMigrations() {
        String[] migrationFiles = {
                "db/migrations/V1__Create_Categories_Table.sql",
                "db/migrations/V2__Create_MenuItems_Table.sql",
                "db/migrations/V3__Create_Users_Table.sql",
                "db/migrations/V4__Create_Orders_Table.sql",
                "db/migrations/V5__Create_OrderDetails_Table.sql",
                "db/migrations/V6__Create_DailyClosing_Table.sql",
                "db/seeds/S1__Seed_Categories.sql",
                "db/seeds/S2__Seed_MenuItems.sql",
                "db/seeds/S3__Seed_Users.sql"
        };

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) return;

            System.out.println("Starting Database Migration...");

            for (String fileName : migrationFiles) {
                executeSqlFile(conn, fileName);
            }

            System.out.println("Migration process finished.");

        } catch (Exception e) {
            System.err.println("Migration failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void executeSqlFile(Connection conn, String filePath) {
        try (InputStream is = DatabaseMigration.class.getClassLoader().getResourceAsStream(filePath)) {
            if (is == null) {
                System.err.println("CRITICAL: File not found in resources: " + filePath);
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String sql = reader.lines().collect(Collectors.joining("\n"));

            String[] statements = sql.split(";");

            try (Statement stmt = conn.createStatement()) {
                for (String sqlStatement : statements) {
                    String trimmedSql = sqlStatement.trim();
                    if (!trimmedSql.isEmpty()) {
                        stmt.execute(trimmedSql);
                    }
                }
                System.out.println("Successfully executed: " + filePath);
            }
        } catch (Exception e) {
            System.err.println("Error in " + filePath + ": " + e.getMessage());
        }
    }
}