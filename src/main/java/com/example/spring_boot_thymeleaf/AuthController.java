package com.example.spring_boot_thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value="error", required=false) String error,
                                Model model)
    {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid credentials");
        }
        return "login";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam("email") String email,
                            @RequestParam("password") String password,
                            Model model)
    {
        boolean ok = LoginHandler.registerUser(email, password);
        if (!ok) {
            model.addAttribute("errorMessage", "Registration failed");
            return "register";
        }
        return "redirect:/login?registered";
    }

}
