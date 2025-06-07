package com.example.spring_boot_thymeleaf;

import org.mindrot.jbcrypt.BCrypt;

public class Hasher {
    public String hash(String password) {
        String salt = BCrypt.gensalt(6);
        String hash = BCrypt.hashpw(password, salt);

        return hash;
    }
}
