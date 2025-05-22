package com.example.spring_boot_thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
public class SpringBootThymeleafApplication {
	public static void main(String[] args) {
		// 1) Load .env from your project root
        Dotenv dotenv = Dotenv.configure()
                              .filename(".env")
                              .load();

        // 2) Push each JDBC variable into System properties
        System.setProperty("JDBC_DATABASE_URL",      dotenv.get("JDBC_DATABASE_URL"));
        System.setProperty("JDBC_DATABASE_USERNAME", dotenv.get("JDBC_DATABASE_USERNAME"));
        System.setProperty("JDBC_DATABASE_PASSWORD", dotenv.get("JDBC_DATABASE_PASSWORD"));

        // 3) Now launch Spring Boot
        SpringApplication.run(SpringBootThymeleafApplication.class, args);
	}

	@GetMapping("/")
    public String home(Model model) {
        model.addAttribute("test", "Home page! This text is coming from the controller. Don't worry if that statement doesn't make sense to you, it barely makes sense to me");
        model.addAttribute("moreTest", "How do I center a div again?");

        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/search")
    public String search(Model model) {
        return "search";
    }

    @GetMapping("/add")
    public String add(Model model) {
        return "add";
    }

    @GetMapping("/help")
    public String help(Model model) {
        return "help";
    }
}