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

    public UserInfo getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserInfo getUserByName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public UserInfo getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public UserInfo getUserBySSN(String ssn) {
        return userRepository.findBySSN(ssn);
    }

    public UserInfo saveUser(UserInfo user) {
        return userRepository.save(user);
    }
}