package com.example.javafx;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class DatabaseConnection2 {
    private static final String URL = "jdbc:postgresql://localhost:5432/StoreManagementSystem";
    private static final String USER = "postgres";
    private static final String PASSWORD = "gio140510";

    private static Connection connection;
    static {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established  ");
        } catch (SQLException e) {
            System.err.println("Failed to create database connection:");
            e.printStackTrace();
        }
    }

    public static boolean registerUser(String username, String password) {
        String sql = "INSERT INTO customer(username, password) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);

            int affectedRows = pstmt.executeUpdate();
            System.out.println("Register: Rows affected = " + affectedRows);
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("SQL Exception during registration:");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean validateUser(String username, String password) {
        String sql = "SELECT password FROM customer WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                return BCrypt.checkpw(password, storedHashedPassword);
            } else {
                System.out.println("User not found: " + username);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception during login:");
            e.printStackTrace();
            return false;
        }
    }


    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed");
            } catch (SQLException e) {
                System.err.println("Failed to close database connection");
                e.printStackTrace();
            }
        }
    }
}
