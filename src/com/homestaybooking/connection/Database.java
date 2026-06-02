package com.homestaybooking.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

public final class Database {
    private static final String URL = System.getProperty("DB_URL", System.getenv().getOrDefault("DB_URL", "jdbc:mysql://127.0.0.1:3307/homestaybooking"));
    private static final String USER = System.getProperty("DB_USER", System.getenv().getOrDefault("DB_USER", "root"));
    private static final String PASSWORD = System.getProperty("DB_PASSWORD", System.getenv().getOrDefault("DB_PASSWORD", "123456"));

    private Database() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String newId() {
        return UUID.randomUUID().toString();
    }
}
