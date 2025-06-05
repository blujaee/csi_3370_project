package com.example.spring_boot_thymeleaf;

import org.mindrot.jbcrypt.BCrypt;

// Default entries in database have a costFactor of 6; 12 is stronger and better for our use

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!! When validating passwords, be sure to the use the correct costFactor !!!
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
public class Hasher {
    public String hash(String password) {
        String salt = BCrypt.gensalt(6);
        String hash = BCrypt.hashpw(password, salt);

        return hash;
    }
}
