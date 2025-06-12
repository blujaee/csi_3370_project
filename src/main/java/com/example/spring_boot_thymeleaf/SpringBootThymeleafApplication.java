package com.example.spring_boot_thymeleaf;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@Controller
public class SpringBootThymeleafApplication {

    private final UserFetcher userFetcher;

    public SpringBootThymeleafApplication(UserFetcher userFetcher) {
        this.userFetcher = userFetcher;
    }

    static {
        // load .env into system properties for both app and tests
        Dotenv dotenv = Dotenv.configure()
                              .filename(".env")
                              .ignoreIfMissing()
                              .load();

        System.setProperty("JDBC_DATABASE_URL",      dotenv.get("JDBC_DATABASE_URL"));
        System.setProperty("JDBC_DATABASE_USERNAME", dotenv.get("JDBC_DATABASE_USERNAME"));
        System.setProperty("JDBC_DATABASE_PASSWORD", dotenv.get("JDBC_DATABASE_PASSWORD"));
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootThymeleafApplication.class, args);
    }

    // Handle both "/" (after login) and "/home"
    @GetMapping({"/", "/home"})
    public String home(Principal principal, Model model) {
        // spring-security will have populated principal.getName() = the user’s email
        String email = principal.getName();

        // fetch the user record from Supabase via your UserFetcher
        UserInfo me = userFetcher.getUserByEmail(email);

        // add to the model for Thymeleaf
        model.addAttribute("firstName", me.getFirstName());
        model.addAttribute("lastName",  me.getLastName());

        return "home";  // resolves to home.html
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @GetMapping("/help")
    public String help() {
        return "help";
    }
}
