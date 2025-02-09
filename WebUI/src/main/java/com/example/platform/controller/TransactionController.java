package com.example.platform.controller;

import com.example.platform.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;  // <-- Add this import
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Controller
public class TransactionController {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public TransactionController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/transaction/{accountNumber}/{transactionId}")
    public String getTransactionDetails(@PathVariable String accountNumber,
                                        @PathVariable String transactionId,
                                        Model model) {
        // API URL to fetch transaction history based on account number
        String url = "http://localhost:8080/api/bank/transaction-history/" + accountNumber;

        try {
            // Fetch transactions from the external API
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);  // Ensure ResponseEntity is used here

            // Deserialize the JSON response into an array of Transaction objects
            Transaction[] transactions = objectMapper.readValue(response.getBody(), Transaction[].class);

            // Find the specific transaction by ID
            for (Transaction transaction : transactions) {
                if (transaction.getId().equals(transactionId)) {
                    model.addAttribute("transaction", transaction);
                    return "transaction-details";  // Render the transaction-details.html page
                }
            }

            // If the transaction is not found, display an error
            model.addAttribute("errorMessage", "Transaction not found.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Unable to fetch transaction details. Please try again later.");
            e.printStackTrace();
        }

        return "transaction-details";  // Render the transaction-details.html page
    }
}
