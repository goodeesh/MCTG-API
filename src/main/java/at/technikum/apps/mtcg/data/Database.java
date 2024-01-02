package at.technikum.apps.mtcg.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String LOCALHOST_URL = "jdbc:postgresql://localhost:5432/mydb";
    private static final String PARALLELS_URL = "jdbc:postgresql://192.168.178.32:5432/mydb";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Try to connect using the localhost URL
            connection = DriverManager.getConnection(LOCALHOST_URL, USERNAME, PASSWORD);
            System.out.println("Connected to localhost");
        } catch (SQLException e) {
            // If connection to localhost fails, try Parallels IP
            System.out.println("Failed to connect to localhost, trying Parallels IP...");
            connection = DriverManager.getConnection(PARALLELS_URL, USERNAME, PASSWORD);
            System.out.println("Connected to Parallels");
        }
        return connection;
    }
}
