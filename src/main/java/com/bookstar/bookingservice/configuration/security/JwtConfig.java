package com.bookstar.bookingservice.configuration.security;

import com.bookstar.bookingservice.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class JwtConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService, UserRepository userRepository) {
        return new JwtAuthenticationFilter(jwtUtil, userDetailsService, userRepository);
    }
}
