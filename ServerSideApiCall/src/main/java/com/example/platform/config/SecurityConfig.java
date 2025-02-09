package com.example.platform.config;

import com.example.platform.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index", "/login", "/register", "/home", "/public-page", "/offers", "/terms", "/privacy", "/cookie", "/accessability", "/services", "/about", "/resources", "/contact", "/disabled", "/disclosures").permitAll()
                .requestMatchers("/private-page", "/dashboard").authenticated()
                .requestMatchers("/transaction/**").authenticated() 
                // Permit access to the H2 console
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/js/**", "/css/**", "/images/**", "/webjars/**", "/static/**").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers("/bank-client/**").authenticated()
                .requestMatchers("/profile", "/verify-identity", "/support").authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/private-page", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
            )
            .csrf(csrf -> csrf.disable())  // Disable CSRF protection for H2 console access
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
