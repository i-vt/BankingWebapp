package com.example.platform.config;

import com.example.platform.service.ActionLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ActionLogService actionLogService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleError(HttpServletRequest request, Exception ex, Model model) {
        String ipAddress = request.getRemoteAddr();
        String requestedUrl = request.getRequestURI();
        
        // Log the error
        actionLogService.logAction(null, "Error on page: " + requestedUrl + " - " + ex.getMessage(), ipAddress);
        
        // Add error message to model
        model.addAttribute("errorMessage", ex.getMessage());
        
        return "error";  // This corresponds to a Thymeleaf error.html page
    }
}
