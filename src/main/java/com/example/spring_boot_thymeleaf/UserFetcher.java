package com.example.spring_boot_thymeleaf;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserFetcher {
    private UserRepository userRepository;
    private JdbcTemplate jdbcTemplate;
    public UserFetcher(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

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

    @Transactional(readOnly = true)
    public List<UserInfo> findAll() {
        return jdbcTemplate.query("select * FROM user_info", new UserRowMapper());
    }
}