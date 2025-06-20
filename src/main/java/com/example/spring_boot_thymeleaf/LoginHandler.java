package com.example.spring_boot_thymeleaf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
public class LoginHandler {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public static boolean authenticateUser(String email, String rawPassword)
        throws ClassNotFoundException, SQLException
    {
        System.out.println("[DEBUG] >>>> Starting authenticateUser() for: " + email);
        Class.forName("org.postgresql.Driver");

        String storedHash;
        try (Connection conn = DriverManager.getConnection(
                 System.getProperty("JDBC_DATABASE_URL"),
                 System.getProperty("JDBC_DATABASE_USERNAME"),
                 System.getProperty("JDBC_DATABASE_PASSWORD")
             );
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT password_hash FROM user_info WHERE email = ?"
             )
        ) {
            System.out.println("[DEBUG] >>>> Sending PreparedStatement to DB.");
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("[DEBUG] >>>> No user found for email: " + email);
                    return false; // no such user
                }
                storedHash = rs.getString("password_hash");
                System.out.println("[DEBUG] >>>> Found hash: " + storedHash);
            }
        }

        return PASSWORD_ENCODER.matches(rawPassword, storedHash);
    }


    public static boolean registerUser(String email, String password) {
        try {
            String hashedPassword = PASSWORD_ENCODER.encode(password);
            Class.forName("org.postgresql.Driver"); // Load PostgreSQL JDBC driver
            try (Connection conn = DriverManager.getConnection(
                    System.getProperty("JDBC_DATABASE_URL"), 
                    System.getProperty("JDBC_DATABASE_USERNAME"), 
                    System.getProperty("JDBC_DATABASE_PASSWORD"))) {
                String query = "INSERT INTO user_info (email, password_hash) VALUES (?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setString(1, email);
                    ps.setString(2, hashedPassword);

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