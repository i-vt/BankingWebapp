package com.example.platform.service;

import com.example.platform.model.Account;
import com.example.platform.model.Transaction;
import com.example.platform.model.User;
import com.example.platform.repository.AccountRepository;
import com.example.platform.repository.TransactionRepository;
import com.example.platform.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;


@Service
public class CryptoBankService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    public List<Account> getAllAccountsForUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new ArrayList<>(user.getAccounts());
    }
    // Get account balance
    public BigDecimal getBalance(String accountNumber) {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        return account.map(Account::getBalance).orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    // Transfer funds between accounts with transaction history
    @Transactional
    public void transferFunds(String fromAccount, String toAccount, BigDecimal amount) {
        Account from = accountRepository.findByAccountNumber(fromAccount)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));
        Account to = accountRepository.findByAccountNumber(toAccount)
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        if (from.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        accountRepository.save(from);
        accountRepository.save(to);

        // Record the transaction for both accounts
        Transaction fromTransaction = new Transaction("TRANSFER", amount.negate(), from);
        Transaction toTransaction = new Transaction("TRANSFER", amount, to);
        transactionRepository.save(fromTransaction);
        transactionRepository.save(toTransaction);
    }

    // Add funds to an account with transaction history
    @Transactional
    public void addFunds(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        // Record the deposit transaction
        Transaction transaction = new Transaction("DEPOSIT", amount, account);
        transactionRepository.save(transaction);
    }

    // Generate random accounts for a user with transaction history
    @Transactional
    public void generateAccountsForUser(UUID userId, int numAccounts) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Random random = new Random();
        for (int i = 0; i < numAccounts; i++) {
            String accountNumber = "ACC" + random.nextInt(10000000);
            BigDecimal balance = BigDecimal.valueOf(random.nextDouble() * 10000).setScale(2, BigDecimal.ROUND_HALF_UP);
            Account account = new Account(accountNumber, balance, user);
            accountRepository.save(account);

            // Record the initial account creation transaction (optional)
            Transaction transaction = new Transaction("ACCOUNT_CREATION", balance, account);
            transactionRepository.save(transaction);
        }
    }

    // Manually add an account with transaction history
    @Transactional
    public Account addAccountToUser(UUID userId, String accountNumber, BigDecimal initialBalance) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Account account = new Account(accountNumber, initialBalance, user);
        accountRepository.save(account);

        // Record the initial deposit (account creation)
        Transaction transaction = new Transaction("ACCOUNT_CREATION", initialBalance, account);
        transactionRepository.save(transaction);

        return account;
    }

    // Delete an account (No transaction needed here, but could be logged if desired)
    @Transactional
    public void deleteAccount(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        accountRepository.delete(account);
    }

    // Create a new user with one default account with a balance of 0 and record the account creation
    @Transactional
    public User createNewUserWithDefaultAccount(String userName) {
        // Create a new user
        User user = new User(userName);
        userRepository.save(user);

        // Create a default account with a balance of 0
        String accountNumber = "ACC" + new Random().nextInt(10000000);
        BigDecimal defaultBalance = BigDecimal.ZERO;

        Account account = new Account(accountNumber, defaultBalance, user);
        accountRepository.save(account);

        // Record the account creation transaction
        Transaction transaction = new Transaction("ACCOUNT_CREATION", defaultBalance, account);
        transactionRepository.save(transaction);

        System.out.println("Created new user: " + user.getName() + " with account: " + account.getAccountNumber() + " (balance: " + account.getBalance() + ")");
        
        return user;
    }

    // Generate random data for testing with transaction history
    @Transactional
    public void generateRandomData() {
        Random random = new Random();
        for (int i = 1; i <= 5; i++) {
            User user = new User("User" + i);
            userRepository.save(user);
            System.out.println("Generated User: " + user.getName() + " with ID: " + user.getId());

            for (int j = 1; j <= 5; j++) {
                String accountNumber = "ACC" + i + j + random.nextInt(10000000);
                BigDecimal balance = BigDecimal.valueOf(random.nextDouble() * 10000).setScale(2, BigDecimal.ROUND_HALF_UP);
                Account account = new Account(accountNumber, balance, user);
                accountRepository.save(account);
                System.out.println("    Account: " + account.getAccountNumber() + " Balance: $" + account.getBalance());

                // Record the account creation transaction
                Transaction transaction = new Transaction("ACCOUNT_CREATION", balance, account);
                transactionRepository.save(transaction);
            }
        }
    }
}
