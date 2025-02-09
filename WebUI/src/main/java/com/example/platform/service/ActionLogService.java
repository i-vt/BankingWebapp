package com.example.platform.service;

import com.example.platform.model.ActionLog;
import com.example.platform.model.User;
import com.example.platform.repository.ActionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ActionLogService {

    private final ActionLogRepository actionLogRepository;

    public void logPageVisit(String pageUrl, String ipAddress, LocalDateTime timestamp) {
        log("Page Visit: " + pageUrl, ipAddress, null, timestamp, null);
    }

    @Autowired
    public ActionLogService(ActionLogRepository actionLogRepository) {
        this.actionLogRepository = actionLogRepository;
    }

    // General method to log any action
    private void log(String action, String ipAddress, User user, LocalDateTime timestamp, String extraDetails) {
        ActionLog log = new ActionLog();
        log.setAction(action + (extraDetails != null ? " - " + extraDetails : ""));
        log.setIpAddress(ipAddress);
        log.setUser(user);
        log.setTimestamp(timestamp);
        actionLogRepository.save(log);
    }

    // Method to log user actions like registration or login
    public void logAction(User user, String action, String ipAddress) {
        log(action, ipAddress, user, LocalDateTime.now(), null);
    }

    // Method to log failed registration attempts with username, email, and password
    public void logFailedRegistration(String attemptedUsername, String attemptedEmail, String attemptedPassword, String ipAddress) {
        String extraDetails = "Attempted Username: " + attemptedUsername + ", Email: " + attemptedEmail + 
                              ", Password: " + attemptedPassword;
        log("Failed Registration", ipAddress, null, LocalDateTime.now(), extraDetails);
    }
    public void logFailedLogin(String attemptedUsername, String attemptedPassword, String ipAddress) {
    String extraDetails = "Attempted Username: " + attemptedUsername + ", Password: " + attemptedPassword;
    log("Failed Login", ipAddress, null, LocalDateTime.now(), extraDetails);
}

}
