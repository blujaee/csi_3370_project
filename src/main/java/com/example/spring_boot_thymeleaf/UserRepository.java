package com.example.spring_boot_thymeleaf;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, String> {
    UserInfo findByEmail(String email);
}
