package com.example.spring_boot_thymeleaf;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
public class SpringBootThymeleafApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootThymeleafApplication.class, args);
	}

	@GetMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("test", "Home page! This text is coming from the controller. Don't worry if that statement doesn't make sense to you, it barely makes sense to me");
        model.addAttribute("moreTest", "How do I center a div again?");
        
        model.addAttribute("people", Arrays.asList(
            new Person("Bob", 20, "bob.lob@gmail.com"), 
            new Person("Jane", 18, "jane.doe@gmail.com"),
            new Person("Larry", 23, "larry123@gmail.com")
        ));

        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/search")
    public String search(Model model) {
		model.addAttribute("email", new EmailForm());
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

	@GetMapping("/patient-info")
    public String info(Model model) {
        return "patient-info";
    }
}