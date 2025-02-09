package com.example.platform.config;

import com.example.platform.service.ActionLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private final ActionLogService actionLogService;

    public RequestLoggingInterceptor(ActionLogService actionLogService) {
        this.actionLogService = actionLogService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ipAddress = request.getRemoteAddr();
        String requestedUrl = request.getRequestURI();

        // Log the initial request visit
        actionLogService.logPageVisit(requestedUrl, ipAddress, LocalDateTime.now());

        return true;  // Continue with the execution of the request
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                            org.springframework.web.servlet.ModelAndView modelAndView) {
        if (response.getStatus() == HttpServletResponse.SC_FOUND) {  // SC_FOUND = 302
            String redirectUrl = response.getHeader("Location");
            if (redirectUrl != null) {
                String ipAddress = request.getRemoteAddr();
                actionLogService.logAction(null, "Redirect to: " + redirectUrl, ipAddress);
            }
        }
    }

}
