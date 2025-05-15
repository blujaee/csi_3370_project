package com.example.spring_boot_thymeleaf;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GetController {
    // GetMapping("/") -> Redirects root of the webserver / to example(.html) for all GET requests
    // See .../main/resources/templates
    @GetMapping("/")
    public String example(Model model) {
        model.addAttribute("test", "This is coming from the controller");
        model.addAttribute("people", Arrays.asList(
            new Person("Bob", 20, "bob.lob@gmail.com"), 
            new Person("Jane", 18, "jane.doe@gmail.com"),
            new Person("Larry", 23, "larry123@gmail.com")
        ));

        return "home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }
}