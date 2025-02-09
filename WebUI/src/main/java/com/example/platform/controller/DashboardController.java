package com.example.platform.controller;

import com.example.platform.model.Account;
import com.example.platform.model.User; // Ensure that the User model is imported
import com.example.platform.service.UserService; // Import your UserService to retrieve user data
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class DashboardController {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final UserService userService;  // Inject UserService to retrieve user details

    @Autowired
    public DashboardController(RestTemplate restTemplate, ObjectMapper objectMapper, UserService userService) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // Assuming the username is unique

        // Fetch the user based on the username
        User user = userService.findByUsername(username);

        if (user == null || user.getBankApiId() == null) {
            model.addAttribute("errorMessage", "Unable to find the user or BANK_API_ID");
            return "dashboard";
        }

        String bankApiId = user.getBankApiId();

        // URL to fetch accounts for the user based on BANK_API_ID
        String url = "http://localhost:8080/api/bank/accounts/" + bankApiId;

        try {
            // Fetch accounts from the external API
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // Convert JSON response to a list of accounts
            List<Account> accounts = objectMapper.readValue(response.getBody(), new TypeReference<List<Account>>() {});

            // Add the accounts to the model to pass to the view
            model.addAttribute("accounts", accounts);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Unable to fetch account data. Please try again later.");
            e.printStackTrace();
        }

        return "dashboard";  // This corresponds to dashboard.html in your templates directory
    }
}
