package com.example.javafx;

import java.sql.*;

public class DatabaseConnection2 {
    public static Connection getConnection() throws SQLException {
        return SMS_DB_Connection.getConnection();
    }

    public static boolean validateCustomer(String username, String password) {
        String sql = "CREATE TABLE IF NOT EXCISTS customer (customer_id SERIAL PRIMARY KEY, username VARCHAR(100), password VARCHAR(100)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registerCustomer(String username, String password) {
        String checkQuery = "SELECT * FROM customer WHERE username = ?";
        String insertQuery = "INSERT INTO customer (username, password) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return false;
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, password);
                insertStmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
