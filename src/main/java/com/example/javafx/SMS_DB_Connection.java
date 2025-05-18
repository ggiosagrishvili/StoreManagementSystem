package com.example.javafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SMS_DB_Connection {
    private static final String URL = "jdbc:postgresql://localhost:5432/StoreManagementSystem";
    private static final String USER = "postgres";
    private static final String PASSWORD = "gio140510";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
