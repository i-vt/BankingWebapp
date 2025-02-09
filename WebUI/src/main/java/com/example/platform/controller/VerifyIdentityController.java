package com.example.platform.controller;

import com.example.platform.model.User;
import com.example.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VerifyIdentityController {

    @Autowired
    private UserService userService;

    @GetMapping("/verify-identity")
    public String verifyIdentityPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Retrieve the current authenticated user
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        // Add the user's bankApiId to the model
        model.addAttribute("user", user);

        // Return the name of the Thymeleaf template
        return "verify-identity";
    }
}
