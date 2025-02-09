package com.example.platform.config;

import com.example.platform.interceptor.RequestLoggingInterceptor;  // Import custom interceptor
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RequestLoggingInterceptor requestLoggingInterceptor;

    // Constructor-based injection for the RequestLoggingInterceptor
    @Autowired
    public WebConfig(RequestLoggingInterceptor requestLoggingInterceptor) {
        this.requestLoggingInterceptor = requestLoggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Add the custom request logging interceptor
        registry.addInterceptor(requestLoggingInterceptor)
                .addPathPatterns("/**")  // Intercept all request paths
                .excludePathPatterns(getExcludedPaths());  // Exclude static resources from being intercepted
    }

    // Method to define the list of excluded paths, such as static resources
    private String[] getExcludedPaths() {
        return new String[]{
            "/css/**",    // Exclude CSS files
            "/js/**",     // Exclude JS files
            "/images/**", // Exclude image files
            "/webjars/**",// Exclude webjars (if used)
            "/static/**", // Exclude static resources
            "/favicon.ico"// Exclude favicon (if used)
        };
    }
}
