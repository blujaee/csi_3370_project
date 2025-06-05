package com.example.spring_boot_thymeleaf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, String> {
    UserInfo findByEmail(String email);
    UserInfo findByFirstNameAndLastName(String firstName, String lastName);
    UserInfo findByPhone(String phone);
    UserInfo findBySSN(String ssn);
    List<UserInfo> findAll();
}