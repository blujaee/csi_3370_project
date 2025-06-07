package com.example.spring_boot_thymeleaf;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserFetcher {
    // Constructor injection
    private UserRepository userRepository;
    private JdbcTemplate jdbcTemplate;
    public UserFetcher(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public User getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public User getUserBySSN(String ssn) {
        return userRepository.findBySSN(ssn);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return jdbcTemplate.query("select * FROM user_info", new UserRowMapper());
    }
}