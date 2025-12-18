package com.example.libris.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        // Allow authentication endpoints
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        // Public endpoints for viewing books
                        .requestMatchers(HttpMethod.GET, "/api/v1/books", "/api/v1/books/*", "/api/v1/books/search").permitAll()
                        
                        // Librarian endpoints for managing books (inventory)
                        .requestMatchers(HttpMethod.POST, "/api/v1/books", "/api/v1/books/instances").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/books/**").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/books/**").hasRole("LIBRARIAN")
                        
                        // Librarian endpoints for managing members
                        .requestMatchers("/api/v1/members/**").hasRole("LIBRARIAN")
                        
                        // Librarian endpoint for checking out books
                        .requestMatchers("/api/v1/loans/checkout").hasRole("LIBRARIAN")

                        // Member endpoints for reservations and history
                        .requestMatchers("/api/v1/reservations/**").hasRole("MEMBER")
                        .requestMatchers("/api/v1/loans/my-history").hasRole("MEMBER")
                        
                        // Any other request must be authenticated
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
