package com.example.spring_boot_thymeleaf;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

@Controller
public class PostController {
    // Automatically instantiates our userfetcher to access the database
    private UserFetcher uf;
    public PostController(UserFetcher uf) {
        this.uf = uf;
    }

    // @RequestParam ... binds form data to a method argument in a controller
    @PostMapping("/submit-search")
    public String submitSearch(@RequestParam("email") String email, Model model) {
        UserInfo myUser = uf.getUserByEmail(email);
        
        model.addAttribute("user", myUser);
        model.addAttribute("email", email);
        
        // Page will go here with patient info if request was successful
        return "patient-info";
    }

    @PostMapping("/submit-add-patient")
    public String submitAddPatient(@RequestParam("email") String email, Model model) {
        UserInfo myUser = uf.getUserByEmail(email);
        
        model.addAttribute("user", myUser);
        model.addAttribute("email", email);
        
        // Page will go here with patient info if request was successful
        return "patient-info";
    }
}