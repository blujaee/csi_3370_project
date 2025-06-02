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

    public static boolean registerUser(String email, String password) {
        try {
            String hashedPassword = PASSWORD_ENCODER.encode(password);
            Class.forName("org.postgresql.Driver"); // Load PostgreSQL JDBC driver
            try (Connection conn = DriverManager.getConnection(System.getProperty("JDBC_DATABASE_URL"), System.getProperty("JDBC_DATABASE_USER"), System.getProperty("JDBC_DATABASE_PASSWORD"))) {
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
    public static boolean authenticateUser(String email, String rawPassword)
            throws ClassNotFoundException, SQLException
    {
        Class.forName("org.postgresql.Driver");
        String sql = 
            "SELECT 1 " +
            "FROM users " +
            "WHERE email = ? " +
            "  AND password = crypt(?, password)";

        try (Connection conn = DriverManager.getConnection(
                System.getProperty("JDBC_DATABASE_URL"),
                System.getProperty("JDBC_DATABASE_USERNAME"),
                System.getProperty("JDBC_DATABASE_PASSWORD")
            );
            PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, email);
            ps.setString(2, rawPassword);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();  // if we get any row back, the password matched
            }
        }
    }
}