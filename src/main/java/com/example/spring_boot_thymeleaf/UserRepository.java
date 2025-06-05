package com.example.spring_boot_thymeleaf;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends CrudRepository<UserInfo, String> {
    UserInfo findByEmail(String email);
    UserInfo findByFirstNameAndLastName(String firstName, String lastName);
    UserInfo findByPhone(String phone);
    UserInfo findBySSN(String ssn);
    @NonNull List<UserInfo> findAll();
}