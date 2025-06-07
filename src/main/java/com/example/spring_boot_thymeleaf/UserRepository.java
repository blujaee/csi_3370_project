package com.example.spring_boot_thymeleaf;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findByEmail(String email);
    User findByFirstNameAndLastName(String firstName, String lastName);
    User findByPhone(String phone);
    User findBySSN(String ssn);
    @NonNull List<User> findAll();
}