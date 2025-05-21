package com.example.javafx;

import java.sql.*;

public class DatabaseConnection2 {
    private static final String URL = "jdbc:postgresql://localhost:5432/StoreManagementSystem";
    private static final String USER = "postgres";
    private static final String PASSWORD = "gio140510";

    public static boolean validateUser(String username, String password) {
        String query = "SELECT * FROM customer WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registerUser(String username, String password) {
        String insert = "INSERT INTO customer (username, password) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insert)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
