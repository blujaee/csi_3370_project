package com.example.spring_boot_thymeleaf;

import java.util.HashMap;
import java.util.Map;

public class LoginHandler {
    private static final Map<String, String> usersDB = new HashMap<>();

    static {
        // Sample user database
        usersDB.put("admin@example.com", "admin123");
        usersDB.put("doctor@clinic.com", "docpass");
        usersDB.put("user@example.com", "userpass");
    }

    /**
     * This method checks login credentials and returns an error code.
     * @param email User's email.
     * @param password User's password.
     * @return Error code: 0 (Success), 1 (Invalid email), 2 (Incorrect password).
     */
    // TODO: Hash user password and compare that against database hash (NOT plaintext)
    public static int loginUser(String email, String password) {
        if (!usersDB.containsKey(email)) {
            return 1; // Invalid email
        } else if (!usersDB.get(email).equals(password)) {
            return 2; // Incorrect password
        } else {
            return 0; // Login successful
        }
    }
}