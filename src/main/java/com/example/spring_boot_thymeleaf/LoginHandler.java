package com.example.spring_boot_thymeleaf;

import java.sql.*;

public class LoginHandler {
    public static boolean registerUser(String email, String password) {
        try {
            Class.forName("org.postgresql.Driver"); // Load PostgreSQL JDBC driver
            try (Connection conn = DriverManager.getConnection(System.getProperty("JDBC_DATABASE_URL"), System.getProperty("JDBC_DATABASE_USER"), System.getProperty("JDBC_DATABASE_PASSWORD"))) {
                String query = "INSERT INTO users (email, password) VALUES (?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setString(1, email);
                    ps.setString(2, password);

                    int affectedRows = ps.executeUpdate();
                    return affectedRows > 0; // Registration successful if rows were inserted
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
            return false;
        } catch (SQLException ex) {
            System.out.println("Database error: " + ex.getMessage());
            return false;
        }
    }
}