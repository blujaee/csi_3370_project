package com.example.spring_boot_thymeleaf;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Controller;

// Form submission and user input handling
@Controller
public class WebController {
    // Constructor injection
    private UserFetcher uf;
    public WebController(UserFetcher uf) {
        this.uf = uf;
    }

    // @RequestParam ... binds form data to a method argument in a controller
    @PostMapping("/submit-search")
    public String submitSearch(@RequestParam("searchType") String searchType, 
                    @RequestParam("searchValuePrimary") String searchValuePrimary, 
                    @RequestParam("searchValueSecondary") String searchValueSecondary, 
                    Model model) {
        User user = new User();

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

        List<User> userList = uf.findAll();
        model.addAttribute("userList", userList);

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
        String passwordHash = hasher.hash(password);

        User user = new User(firstName, lastName, phone, SSN, address, birthdate, email, role, dateJoined, passwordHash);
        
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
        List<User> userList = uf.findAll();
        
        model.addAttribute("userList", userList);
        System.out.println(userList.get(0).getEmail());
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