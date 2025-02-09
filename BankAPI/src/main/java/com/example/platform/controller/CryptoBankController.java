package com.example.platform.controller;

import com.example.platform.model.User;
import com.example.platform.dto.TransactionDTO;
import com.example.platform.model.Account;
import com.example.platform.model.Transaction;
import com.example.platform.repository.AccountRepository;
import com.example.platform.service.CryptoBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bank")
public class CryptoBankController {

    @Autowired
    private CryptoBankService bankService;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/transaction-history/{accountNumber}")
    public List<TransactionDTO> getTransactionHistory(@PathVariable String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Convert each Transaction to a TransactionDTO to avoid returning full Account and User objects
        List<TransactionDTO> transactionHistory = new ArrayList<>();
        for (Transaction transaction : account.getTransactions()) {
            transactionHistory.add(new TransactionDTO(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getTimestamp(),
                transaction.getAccount().getAccountNumber()  // Only include account number, not full object
            ));
        }

        return transactionHistory;
    }

    @PostMapping("/transfer")
    public void transferFunds(@RequestParam String fromAccount, @RequestParam String toAccount, @RequestParam BigDecimal amount) {
        bankService.transferFunds(fromAccount, toAccount, amount);
    }

    @PostMapping("/add-funds")
    public void addFunds(@RequestParam String accountNumber, @RequestParam BigDecimal amount) {
        bankService.addFunds(accountNumber, amount);
    }

    @PostMapping("/generate-accounts")
    public void generateAccountsForUser(@RequestParam UUID userId, @RequestParam int numAccounts) {
        bankService.generateAccountsForUser(userId, numAccounts);
    }

    @PostMapping("/add-account")
    public void addAccount(@RequestParam UUID userId, @RequestParam String accountNumber, @RequestParam BigDecimal initialBalance) {
        bankService.addAccountToUser(userId, accountNumber, initialBalance);
    }

    @DeleteMapping("/delete-account/{accountId}")
    public void deleteAccount(@PathVariable UUID accountId) {
        bankService.deleteAccount(accountId);
    }

    @PostMapping("/create-user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestParam String userName) {
        return bankService.createNewUserWithDefaultAccount(userName);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }
    @GetMapping("/balance/{accountNumber}")
    public BigDecimal getBalance(@PathVariable String accountNumber) {
        return bankService.getBalance(accountNumber);
    }  
    @GetMapping("/accounts/{userId}")
    public List<Account> getAllAccountsForUser(@PathVariable UUID userId) {
        return bankService.getAllAccountsForUser(userId);
    }

}
