package com.example.platform;

import com.example.platform.service.CryptoBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;  // Use Jakarta PostConstruct

@SpringBootApplication
public class PlatformApplication {

    @Autowired
    private CryptoBankService cryptoBankService;

    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }

    @PostConstruct
    public void init() {
        cryptoBankService.generateRandomData();
        System.out.println("Random users and accounts generated at startup");
    }
}
