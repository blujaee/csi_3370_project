package com.example.spring_boot_thymeleaf;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

@RestController
public class EmailController {
    @PostMapping("/submit-email")
    public String submitEmail(@ModelAttribute EmailForm emailObj, Model model) {
        String email = emailObj.getEmail();

        System.out.println("Received email: " + email);

        // Page will go here with patient info if request was successful
        return "patient-info";
    }
}