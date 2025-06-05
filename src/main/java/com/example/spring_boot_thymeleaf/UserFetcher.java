package com.example.spring_boot_thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFetcher {
    @Autowired
    private UserRepository userRepository;

    public UserInfo getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
