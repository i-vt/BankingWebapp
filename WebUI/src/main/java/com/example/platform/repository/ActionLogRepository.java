package com.example.platform.repository;

import com.example.platform.model.ActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {
    // Custom queries can be added here if necessary
}
