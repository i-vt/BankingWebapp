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
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String getProfilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Get the logged-in user's username from the security context
        String username = userDetails.getUsername();

        // Retrieve the user information using the username
        User user = userService.findByUsername(username);

        // Add the user information to the model
        model.addAttribute("user", user);

        // Return the profile view
        return "profile";
    }
}
