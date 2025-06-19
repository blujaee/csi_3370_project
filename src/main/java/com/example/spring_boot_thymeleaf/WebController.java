package com.example.spring_boot_thymeleaf;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

// Form submission and user input handling
@Controller
public class WebController {
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

        List<UserInfo> userList = uf.findAll();
        model.addAttribute("userList", userList);

        // Page will go here with patient info if request was successful
        return "patient-info";
    }

    @PostMapping("/add-user")
    public String submitAddPatient(@RequestParam("email") String email, 
                        @RequestParam("firstName") String firstName, 
                        @RequestParam("lastName") String lastName, 
                        @RequestParam("phone") String phone, 
                        @RequestParam("ssn") String SSN, 
                        @RequestParam("role") String role, 
                        @RequestParam("birthdate") String birthdate, 
                        @RequestParam("address") String address, 
                        @RequestParam("password") String password,
                        @RequestParam("password") String oldPasswordHash,
                        Model model) {

        String dateJoined = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        String passwordHash;
        if (oldPasswordHash.strip() != "") {
            Hasher hasher = new Hasher();
            passwordHash = hasher.hash(password);
        } else {
            passwordHash = oldPasswordHash;
        }

        boolean error = false;
        if (!UserInfo.validatePhoneFormat(phone) || !UserInfo.validateSSNFormat(SSN)) {
            System.out.println("Exception saving user info to database: Phone or SSN string are incorrectly formatted.");
            error = true;
        } else {
            phone = phone.replaceAll("-", "");
        }

        UserInfo user = new UserInfo(firstName, lastName, phone, SSN, address, birthdate, email, role, dateJoined, passwordHash);
        user.setId(null);
        try {
            uf.saveUser(user);
        } catch (Exception e) {
            System.out.println("Exception saving user info to database: " + e.getMessage());
            error = true;
        }

        if (error) {
            model.addAttribute("firstName", "");
            model.addAttribute("lastName", "");
            model.addAttribute("phone", "");
            model.addAttribute("ssn", "");
            model.addAttribute("address", "");
            model.addAttribute("birthdate", "");
            model.addAttribute("email", "");
            model.addAttribute("dateJoined", "");
            model.addAttribute("passwordHash", "");
            model.addAttribute("role", "");
            model.addAttribute("address", "");

            model.addAttribute("error", error);
            model.addAttribute("updated", true);
            return "edit";
        }
        
        model.addAttribute("user", user);
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
        model.addAttribute("error", error);

        // Page will go here with patient info if request was successful
        return "add-confirmation";
    }

    @GetMapping("/")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = null;
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User userDetails = (User) authentication.getPrincipal();
            email = userDetails.getUsername();
        }
        UserInfo user = uf.getUserByEmail(email);

        model.addAttribute("user", user);

        return "home";
    }

    // USER must have role MEDICAL_STAFF to access database search
    @PreAuthorize("hasRole('ROLE_MEDICAL_STAFF')")
    @GetMapping("/search")
    public String search(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = null;
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User userDetails = (User) authentication.getPrincipal();
            email = userDetails.getUsername();
        }
        UserInfo user = uf.getUserByEmail(email);

        model.addAttribute("user", user);

        List<UserInfo> userList = uf.findAll();
        
        model.addAttribute("userList", userList);
        return "search";
    }

    @GetMapping("/add")
    public String add(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = null;
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User userDetails = (User) authentication.getPrincipal();
            email = userDetails.getUsername();
        }
        UserInfo user = uf.getUserByEmail(email);

        model.addAttribute("user", user);

        return "add";
    }

    @GetMapping("/help")
    public String help(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = null;
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User userDetails = (User) authentication.getPrincipal();
            email = userDetails.getUsername();
        }
        UserInfo user = uf.getUserByEmail(email);

        model.addAttribute("user", user);

        return "help";
    }

    @GetMapping("/edit")
    public String edit(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = null;
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User userDetails = (User) authentication.getPrincipal();
            email = userDetails.getUsername();
        }
        UserInfo user = uf.getUserByEmail(email);

        model.addAttribute("user", user);
        model.addAttribute("updated", false);
        return "edit";
    }

    @PostMapping("/update-user")
    public String updateUser(@RequestParam("email") String email, 
                        @RequestParam("firstName") String firstName, 
                        @RequestParam("lastName") String lastName, 
                        @RequestParam("phone") String phone, 
                        @RequestParam("ssn") String SSN, 
                        @RequestParam("role") String role, 
                        @RequestParam("birthdate") String birthdate, 
                        @RequestParam("address") String address, 
                        @RequestParam("password") String password,
                        @RequestParam("dateJoined") String dateJoined,
                        @RequestParam("id") String id,
                        Model model) {
        Hasher hasher = new Hasher();
        String passwordHash = hasher.hash(password);

        boolean error = false;
        if (!UserInfo.validatePhoneFormat(phone) || !UserInfo.validateSSNFormat(SSN)) {
            System.out.println("Exception saving user info to database: Phone or SSN string are incorrectly formatted.");
            error = true;
        } else {
            phone = phone.replaceAll("-", "");
        }

        UserInfo user = new UserInfo(firstName, lastName, phone, SSN, address, birthdate, email, role, dateJoined, passwordHash);
        user.setId(id);
        try {
            uf.saveUser(user);
        } catch (Exception e) {
            System.out.println("Exception saving user info to database: " + e.getMessage());
            error = true;
        }

        if (error) {
            model.addAttribute("firstName", "");
            model.addAttribute("lastName", "");
            model.addAttribute("phone", "");
            model.addAttribute("ssn", "");
            model.addAttribute("address", "");
            model.addAttribute("birthdate", "");
            model.addAttribute("email", "");
            model.addAttribute("dateJoined", "");
            model.addAttribute("passwordHash", "");
            model.addAttribute("role", "");
            model.addAttribute("address", "");

            model.addAttribute("error", error);
            model.addAttribute("updated", true);
            return "edit";
        }
        
        model.addAttribute("user", user);
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

        model.addAttribute("error", error);
        model.addAttribute("updated", true);
        return "edit";
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value="error", required=false) String error,
                                Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = null;
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User userDetails = (User) authentication.getPrincipal();
            email = userDetails.getUsername();
        }
        UserInfo user = uf.getUserByEmail(email);

        model.addAttribute("user", user);

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