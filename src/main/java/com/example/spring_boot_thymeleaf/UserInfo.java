package com.example.spring_boot_thymeleaf;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("user_info")
public class UserInfo {
    @Id
    @Column("id")
    private String id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("phone")
    private String phone;

    @Column("ssn")
    private String SSN;

    @Column("address")
    private String address;

    @Column("birthdate")
    private String birthdate;

    @Column("email")
    private String email;

    @Column("date_joined")
    private String dateJoined;

    @Column("role")
    private String role;

    @Column("password_hash")
    private String passwordHash;

    public UserInfo() {}

    // String id
    public UserInfo(String firstName, String lastName, String phone, String SSN,
                    String address, String birthdate, String email, String role, String dateJoined, String passwordHash) {
        // this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.SSN = SSN;
        this.address = address;
        this.birthdate = birthdate;
        this.email = email;
        this.role = role;
        this.dateJoined = dateJoined;
        this.passwordHash = passwordHash;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }
    
    public String getSSN() {
        return SSN;
    }

    public String getAddress() {
        return address;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getRole() {
        return role;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
