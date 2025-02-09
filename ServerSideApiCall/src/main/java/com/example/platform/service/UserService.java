package com.example.platform.service;

import com.example.platform.model.User;
import com.example.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);  // Logger instance

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();  // Initialize ObjectMapper directly
    }

    public User save(User user, String ipAddress) throws Exception {
        // Check for duplicate username and email
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new Exception("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new Exception("Email already exists");
        }

        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user in the local database
        User savedUser = userRepository.save(user);

        // Log the API request before sending it
        logger.info("Sending API request to create bank user: {}", user.getUsername());

        // Create the user in the external bank API
        String bankApiId = createBankUser(user.getUsername());

        // Log the response from the API
        logger.info("Received bankApiId: {}", bankApiId);

        // Update the user with the returned bankApiId and save it
        savedUser.setBankApiId(bankApiId);
        return userRepository.save(savedUser);
    }

    private String createBankUser(String username) throws Exception {
        String bankApiUrl = "http://localhost:8080/api/bank/create-user";

        // Prepare the request payload using MultiValueMap for form-urlencoded data
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("userName", username);

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create HttpEntity with the request body and headers
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);

        // Log the outgoing request details
        logger.info("Outgoing request to Bank API at {} with body: {}", bankApiUrl, requestBody);

        // Send POST request to create the user in the bank system and capture response
        ResponseEntity<String> response = restTemplate.exchange(bankApiUrl, HttpMethod.POST, entity, String.class);

        // Log the full API response
        logger.info("Received full response from Bank API: {}", response.getBody());

        // Extract the bank_api_id from the response
        return extractBankApiIdFromResponse(response.getBody());
    }

    // Helper method to extract the bank_api_id from the response
    private String extractBankApiIdFromResponse(String response) throws Exception {
        // Assuming the response is in JSON format: {"id":"some-id","name":"JohnDoe"}
        try {
            Map<String, String> responseMap = objectMapper.readValue(response, new TypeReference<Map<String, String>>() {});
            return responseMap.get("id");
        } catch (Exception e) {
            logger.error("Failed to parse bank API response", e);
            throw new Exception("Failed to parse bank API response");
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);  // Ensure this method exists in UserRepository
    }

    public void updateLoginInfo(User user, String ipAddress) {
        user.setLastLoginIp(ipAddress);
        user.setLastLoginTime(java.time.LocalDateTime.now());
        userRepository.save(user);
    }
}
