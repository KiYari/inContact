package com.gruppa.incontact.accounts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain httpFilter(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(req -> {
            req.anyRequest().permitAll();
        })
                .csrf().disable();

        return http.build();
    }
}
