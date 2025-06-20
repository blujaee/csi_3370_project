package com.example.spring_boot_thymeleaf;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.dao.DuplicateKeyException;
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
                            @RequestParam(value = "searchValueSecondary", required = false) String searchValueSecondary,
                            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = null;
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User userDetails = (User) authentication.getPrincipal();
            email = userDetails.getUsername();
        }
        UserInfo user = uf.getUserByEmail(email);

        model.addAttribute("user", user);

        List<UserInfo> userList = uf.findAll();

        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValuePrimary", searchValuePrimary);
        model.addAttribute("searchValueSecondary", searchValueSecondary);
        if (searchValuePrimary == null || searchValuePrimary.isEmpty()) {
            model.addAttribute("userList", userList);
            model.addAttribute("filtered", false);
            return "search";
        }

        String search = searchValuePrimary.toLowerCase();

        List<UserInfo> filteredUsers = userList.stream()
            .filter(u -> {
                switch (searchType.toLowerCase()) {
                    case "name":
                        return u.getFirstName().toLowerCase().contains(search) || 
                        u.getLastName().toLowerCase().contains(search);
                    case "email":
                        return u.getEmail().toLowerCase().contains(search);
                    case "id":
                        return u.getId().toLowerCase().contains(search);
                    case "phone":
                        return u.getPhone().toLowerCase().contains(search);
                    case "ssn":
                        return u.getSSN().toLowerCase().contains(search);
                    default:
                        return u.getId().toLowerCase().contains(search);
                }
            })
        .collect(Collectors.toList());

        model.addAttribute("userList", filteredUsers);
        model.addAttribute("filtered", true);
        return "search";
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
                        @RequestParam("passwordConfirmation") String passwordConfirmation,
                        Model model) {

        boolean error = false;
        String errorMessage = "";
        String dateJoined = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        String passwordHash = "";
        if (!password.isBlank()) {
            if (password.equals(passwordConfirmation)) {
                Hasher hasher = new Hasher();
                passwordHash = hasher.hash(password.strip());
            } else {
                errorMessage = "Passwords do not match.";
                error = true;
            }
        } else {
            System.out.println("Exception saving user info to database: Password is blank.");
            errorMessage = "Passwords are blank.";
        }

        if (!UserInfo.validatePhoneFormat(phone) || !UserInfo.validateSSNFormat(SSN)) {
            System.out.println("Exception saving user info to database: Phone or SSN string are incorrectly formatted.");
            errorMessage = "Phone or SSN are incorrectly formatted.";
            error = true;
        } else {
            phone = phone.replaceAll("-", "");
        }

        firstName = firstName.strip();
        lastName = lastName.strip();
        phone = phone.strip();
        SSN = SSN.strip();
        address = address.strip();
        birthdate = birthdate.strip();
        email = email.strip();
        dateJoined = dateJoined.strip();
        UserInfo user = new UserInfo(firstName, lastName, phone, SSN, address, birthdate, email, role, dateJoined, passwordHash);
        user.setId(null);

        try {
            uf.saveUser(user);
        } catch (DuplicateKeyException e) {
            System.out.println("Exception: One or more keys already exist in database: " + e.getMessage());
            e.printStackTrace();
            errorMessage = "Database rejected credentials.";
            error = true;
        } catch (Exception e) {
            System.out.println("Exception saving user info to database: " + e.getMessage());
            e.printStackTrace();
            errorMessage = "Database rejected credentials.";
            error = true;
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

        model.addAttribute("error", error);
        model.addAttribute("updated", true);
        model.addAttribute("errorMessage", errorMessage);

        return "add";
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
        
        model.addAttribute("searchValuePrimary", "");
        model.addAttribute("searchValueSecondary", "");
        model.addAttribute("userList", userList);
        model.addAttribute("filtered", false);
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
                        @RequestParam("passwordConfirmation") String passwordConfirmation,
                        @RequestParam("oldPasswordHash") String oldPasswordHash,
                        @RequestParam("dateJoined") String dateJoined,
                        @RequestParam("id") String id,
                        Model model) {

        String errorMessage = "";
        boolean error = false;

        String passwordHash = "";
        if (!password.isBlank()) {
            if (password.equals(passwordConfirmation)) {
                Hasher hasher = new Hasher();
                passwordHash = hasher.hash(password.strip());
            } else {
                errorMessage = "Error: Passwords do not match.";
                error = true;
            }
        } else {
            passwordHash = oldPasswordHash;
        }

        if (!UserInfo.validatePhoneFormat(phone) || !UserInfo.validateSSNFormat(SSN)) {
            System.out.println("Exception saving user info to database: Phone or SSN string are incorrectly formatted.");
            error = true;
        } else {
            phone = phone.replaceAll("-", "");
        }

        firstName = firstName.strip();
        lastName = lastName.strip();
        phone = phone.strip();
        SSN = SSN.strip();
        address = address.strip();
        birthdate = birthdate.strip();
        email = email.strip();
        dateJoined = dateJoined.strip();
        UserInfo user = new UserInfo(firstName, lastName, phone, SSN, address, birthdate, email, role, dateJoined, passwordHash);
        user.setId(id);
        try {
            uf.saveUser(user);
        } catch (Exception e) {
            System.out.println("Exception saving user info to database: " + e.getMessage());
            error = true;
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

        model.addAttribute("error", error);
        model.addAttribute("updated", true);
        model.addAttribute("errorMessage", errorMessage);
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