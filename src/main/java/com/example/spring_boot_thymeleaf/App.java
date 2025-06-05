package com.example.spring_boot_thymeleaf;
// import java.sql.*;
// import java.util.Scanner;

public class App {
    // public static void main(String[] args) {
    //     Scanner scanner = new Scanner(System.in);

    //     System.out.println("Enter Email:");
    //     String email = scanner.nextLine();

    //     System.out.println("Enter Password:");
    //     String password = scanner.nextLine();

    //     String phone;
    //     while (true) {
    //         System.out.println("Enter Phone Number:");
    //         phone = scanner.nextLine();
    //         if (phone.matches("\d+")) break;
    //         System.out.println("Invalid input. Please enter only numbers.");
    //     }

    //     String birthdate;
    //     while (true) {
    //         System.out.println("Enter Birthdate (YYYY-MM-DD):");
    //         birthdate = scanner.nextLine();
    //         if (birthdate.matches("\d{4}-\d{2}-\d{2}")) break;
    //         System.out.println("Invalid date format. Please use YYYY-MM-DD.");
    //     }

    //     String ssn;
    //     while (true) {
    //         System.out.println("Enter SSN (9 digits):");
    //         ssn = scanner.nextLine();
    //         if (ssn.matches("\d{9}")) break;
    //         System.out.println("Invalid SSN. Please enter a 9-digit number.");
    //     }

    //     System.out.println("Enter Address:");
    //     String address = scanner.nextLine();

    //     System.out.println("Enter Role:");
    //     String role = scanner.nextLine();

    //     if (registerUser(email, password, phone, birthdate, ssn, address, role)) {
    //         System.out.println("Registration successful!");
    //     } else {
    //         System.out.println("Registration failed. Email may already exist.");
    //     }

    //     scanner.close();
    // }

    // public static boolean registerUser(String email, String password, String phone, String birthdate, String ssn, String address, String role) {
    //     try {
    //         Class.forName("org.postgresql.Driver");
    //         try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
    //             String query = "INSERT INTO users (email, password, phone, birthdate, ssn, address, date_joined, role) VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";
    //             try (PreparedStatement ps = conn.prepareStatement(query)) {
    //                 ps.setString(1, email);
    //                 ps.setString(2, password);
    //                 ps.setString(3, phone);
    //                 ps.setString(4, birthdate);
    //                 ps.setString(5, ssn);
    //                 ps.setString(6, address);
    //                 ps.setString(7, role);

    //                 int affectedRows = ps.executeUpdate();
    //                 return affectedRows > 0;
    //             }
    //         }
    //     } catch (ClassNotFoundException e) {
    //         System.out.println("PostgreSQL JDBC Driver not found.");
    //         e.printStackTrace();
    //         return false;
    //     } catch (SQLException ex) {
    //         System.out.println("Database error: " + ex.getMessage());
    //         return false;
    //     }
    // }
}