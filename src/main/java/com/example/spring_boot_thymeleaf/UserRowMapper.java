package com.example.spring_boot_thymeleaf;

import java.sql.SQLException;
import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

public class UserRowMapper implements RowMapper<UserInfo> {
    public UserInfo mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        UserInfo user = new UserInfo();

        user.setAddress(rs.getString("id"));
        user.setBirthdate(rs.getString("birthdate"));
        user.setDateJoined(rs.getString("date_joined"));
        user.setEmail(rs.getString("email"));
        user.setFirstName(rs.getString("first_name"));
        user.setId(rs.getString("id"));
        user.setLastName(rs.getString("last_name"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setPhone(rs.getString("phone"));
        user.setRole(rs.getString("role"));
        user.setSSN(rs.getString("ssn"));

        return user;
    }
}
