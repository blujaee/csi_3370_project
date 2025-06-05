package com.example.spring_boot_thymeleaf;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Controller;

// Form submission and user input handling
@Controller
public class PostController {
    private UserFetcher uf;
    private UserRepository up;
    public PostController(UserFetcher uf, UserRepository up) {
        this.uf = uf;
        this.up = up;
    }

    // @RequestParam ... binds form data to a method argument in a controller
    @PostMapping("/submit-search")
    public String submitSearch(@RequestParam("searchType") String searchType, 
                    @RequestParam("searchValuePrimary") String searchValuePrimary, 
                    @RequestParam("searchValueSecondary") String searchValueSecondary, 
                    Model model) {
        UserInfo user = new UserInfo();

        switch (searchType) {
            case "email":
                user = uf.getUserByEmail(searchValuePrimary);
                break;
            case "id":
                user = uf.getUserById(searchValuePrimary);
                break;
            case "phone":
                user = uf.getUserByPhone(searchValuePrimary);
                break;
            case "name":
                user = uf.getUserByName(searchValuePrimary, searchValueSecondary);
                break;
            case "ssn":
                user = uf.getUserBySSN(searchValuePrimary);
                break;
        }
        
        model.addAttribute("user", user);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValuePrimary", searchValuePrimary);
        model.addAttribute("searchValueSecondary", searchValueSecondary);
        
        // Page will go here with patient info if request was successful
        return "patient-info";
    }

    @PostMapping("/submit-add-patient")
    public String submitAddPatient(@RequestParam("email") String email, 
                        @RequestParam("firstName") String firstName, 
                        @RequestParam("lastName") String lastName, 
                        @RequestParam("phone") String phone, 
                        @RequestParam("ssn") String SSN, 
                        @RequestParam("role") String role, 
                        @RequestParam("birthdate") String birthdate, 
                        @RequestParam("address") String address, 
                        @RequestParam("password") String password,
                        Model model) {

        String dateJoined = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        Hasher hasher = new Hasher();
        String passwordHash = hasher.hash(password, 12);

        // String id = UUID.randomUUID().toString();
        UserInfo user = new UserInfo(firstName, lastName, phone, SSN, address, birthdate, email, role, dateJoined, passwordHash);
        
        user.setId(null);
        uf.saveUser(user);
 
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("phone", phone);
        model.addAttribute("ssn", SSN);
        model.addAttribute("address", address);
        model.addAttribute("birthdate", birthdate);
        model.addAttribute("email", email);
        model.addAttribute("dateJoined", dateJoined);
        model.addAttribute("passwordHash", passwordHash);
        model.addAttribute("role", role);
        model.addAttribute("address", address);
        
        // Page will go here with patient info if request was successful
        return "add-confirmation";
    }
}