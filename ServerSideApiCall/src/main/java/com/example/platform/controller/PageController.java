package com.example.platform.controller;

import com.example.platform.model.User;
import com.example.platform.service.UserService;
import com.example.platform.service.ActionLogService;  // Import ActionLogService
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class PageController {

    private final UserService userService;
    private final ActionLogService actionLogService;  // Declare ActionLogService

    // Constructor-based dependency injection (recommended)
    @Autowired
    public PageController(UserService userService, ActionLogService actionLogService) {
        this.userService = userService;
        this.actionLogService = actionLogService;  // Inject ActionLogService
    }

    @GetMapping("/")
    public String redirectToIndex() {
        return "index";  // Corresponds to index.html
    }

    @GetMapping("/home")
    public String home() {
        return "home";  // Corresponds to home.html
    }

    @GetMapping("/terms")
    public String terms() {
        return "terms-of-service";  // Corresponds to terms-of-service.html
    }

    @GetMapping("/disclosures")
    public String disclosures() {
        return "disclosures";  // Corresponds to disclosures.html
    }    

    @GetMapping("/privacy")
    public String privacy() {
        return "privacy-policy";  // Corresponds to privacy-policy.html
    }  

    @GetMapping("/cookie")
    public String cookie() {
        return "cookie-policy";  // Corresponds to cookie-policy.html
    }    

    @GetMapping("/accessibility")
    public String accessibility() {
        return "accessibility";  // Corresponds to accessibility.html
    }    

    @GetMapping("/services")
    public String services() {
        return "services";  // Corresponds to services.html
    }    

    @GetMapping("/about")
    public String aboutPage() {
        return "about";  // Corresponds to about.html
    }    

    @GetMapping("/resources")
    public String resources() {
        return "resources";  // Corresponds to resources.html
    }    

    @GetMapping("/contact")
    public String contact() {
        return "contact";  // Corresponds to contact.html
    }    

    @GetMapping("/support")
    public String support() {
        return "support";  // Corresponds to support.html
    }    

    @GetMapping("/public-page")
    public String publicPage() {
        return "public-page";  // Corresponds to public-page.html
    }

    @GetMapping("/offers")
    public String offersPage() {
        return "offers";  // Corresponds to offers.html
    }

    @GetMapping("/disabled")
    public String disabledPage() {
        return "disabled";  // Corresponds to disabled.html
    }

    @GetMapping("/private-page")
    public String privatePage(Authentication authentication, HttpServletRequest request) {
        if (authentication != null) {
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            if (user != null) {
                String ipAddress = request.getRemoteAddr();
                userService.updateLoginInfo(user, ipAddress);  // Call updateLoginInfo in UserService
                actionLogService.logAction(user, "login", ipAddress);  // Log login action
            }
        }
        return "private-page";  // Corresponds to private-page.html
    }

    @GetMapping("/login")
    public String login() {
        return "login";  // Corresponds to login.html
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";  // Corresponds to register.html
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result, HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            // Add error message if validation fails
            model.addAttribute("errorMessage", "Please correct the errors in the form.");
            return "register";
        }

        String ipAddress = request.getRemoteAddr();  // Capture IP address of the request
        try {
            // Attempt to save the user and log their IP address
            userService.save(user, ipAddress);
            return "redirect:/login";
        } catch (Exception e) {
            // Log the failed registration and show the error message
            model.addAttribute("errorMessage", e.getMessage());
            return "register";  // Reload the registration page with the error message
        }
    }
}
