package com.example.platform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/bank-client")
public class BankController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/api/bank";

    // 1. Get Account Balance
    @GetMapping("/balance")
    @ResponseBody
    public ResponseEntity<String> getAccountBalance(@RequestParam String accountNumber) {
        // Check authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated user: " + authentication.getName());

        String url = baseUrl + "/balance/{accountNumber}";
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("accountNumber", accountNumber);

        return restTemplate.getForEntity(url, String.class, uriVariables);
    }

    // 2. Transfer Funds Between Accounts
    @PostMapping("/transfer")
    @ResponseBody
    public ResponseEntity<String> transferFunds(
            @RequestParam String fromAccount, 
            @RequestParam String toAccount, 
            @RequestParam String amount) {
        
        // Check authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated user: " + authentication.getName());

        String url = baseUrl + "/transfer";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("fromAccount", fromAccount)
                .queryParam("toAccount", toAccount)
                .queryParam("amount", amount);
        
        return restTemplate.postForEntity(builder.toUriString(), null, String.class);
    }

    // 3. Add Funds to an Account
    @PostMapping("/add-funds")
    @ResponseBody
    public ResponseEntity<String> addFunds(@RequestParam String accountNumber, @RequestParam String amount) {
        // Check authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated user: " + authentication.getName());

        String url = baseUrl + "/add-funds";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("accountNumber", accountNumber)
                .queryParam("amount", amount);

        return restTemplate.postForEntity(builder.toUriString(), null, String.class);
    }

    // 4. Generate Accounts for a User
    @PostMapping("/generate-accounts")
    @ResponseBody
    public ResponseEntity<String> generateAccounts(
            @RequestParam String userId, 
            @RequestParam int numAccounts) {
        
        // Check authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated user: " + authentication.getName());

        String url = baseUrl + "/generate-accounts";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("userId", userId)
                .queryParam("numAccounts", numAccounts);

        return restTemplate.postForEntity(builder.toUriString(), null, String.class);
    }

    // 5. Add Account to a User
    @PostMapping("/add-account")
    @ResponseBody
    public ResponseEntity<String> addAccount(
            @RequestParam String userId, 
            @RequestParam String accountNumber, 
            @RequestParam String initialBalance) {
        
        // Check authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated user: " + authentication.getName());

        String url = baseUrl + "/add-account";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("userId", userId)
                .queryParam("accountNumber", accountNumber)
                .queryParam("initialBalance", initialBalance);

        return restTemplate.postForEntity(builder.toUriString(), null, String.class);
    }

    // 6. Delete an Account
    @DeleteMapping("/delete-account")
    @ResponseBody
    public ResponseEntity<String> deleteAccount(@RequestParam String accountId) {
        // Check authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated user: " + authentication.getName());

        String url = baseUrl + "/delete-account/{accountId}";
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("accountId", accountId);

        restTemplate.delete(url, uriVariables);
        return ResponseEntity.ok("Account deleted");
    }

    // 7. Create a New User with a Default Account
    @PostMapping("/create-user")
    @ResponseBody
    public ResponseEntity<String> createUser(@RequestParam String userName) {
        // Check authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated user: " + authentication.getName());

        String url = baseUrl + "/create-user";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("userName", userName);

        return restTemplate.postForEntity(builder.toUriString(), null, String.class);
    }
}
