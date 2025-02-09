package com.example.platform.service;

import com.example.platform.model.User;  // Import your own User entity
import com.example.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActionLogService actionLogService;

    @Autowired
    private HttpServletRequest request;  // To access the client's IP address

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String ipAddress = request.getRemoteAddr();
        String password = request.getParameter("password");  // Capture the password entered by the user
        com.example.platform.model.User user = userRepository.findByUsername(username);  // Use fully qualified name

        if (user == null) {
            // Log failed login attempt for non-existent username with the provided password
            actionLogService.logFailedLogin(username, password, ipAddress);  // Updated: removed the "failed login" argument
            throw new UsernameNotFoundException("User not found");
        }

        // Fully qualify the Spring Security User class
        return org.springframework.security.core.userdetails.User.withUsername(username)
                   .password(user.getPassword())
                   .roles("USER")
                   .build();
    }
}
