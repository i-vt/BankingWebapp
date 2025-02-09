package com.example.platform.repository;

import com.example.platform.model.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {
    LoginAttempt findByUserId(UUID userId);
}
