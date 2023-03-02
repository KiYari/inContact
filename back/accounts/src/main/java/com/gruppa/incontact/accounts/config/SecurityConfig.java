package com.gruppa.incontact.accounts.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppa.incontact.accounts.config.filters.JwtRequestFilter;
import com.gruppa.incontact.accounts.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(UserService userService, JwtRequestFilter jwtRequestFilter) {
        this.userService = userService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain httpRequestsFilter(HttpSecurity http)
                                throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeHttpRequests(req -> {
                        req
                                .requestMatchers("/api/auth/**").permitAll()
                                .anyRequest().authenticated();
                    }
                )
                .exceptionHandling(e -> {
                    e.authenticationEntryPoint((req, res, authException) -> {
                        Map<String, Object> responseMap = new HashMap<>();
                        ObjectMapper mapper = new ObjectMapper();
                        System.out.println("excepted");
                        res.setStatus(401);
                        responseMap.put("error", true);
                        responseMap.put("message", "Unauthorized request. Plesae authorize yourself");
                        res.setHeader("content-type", "application/json");
                        res.setHeader("Access-Control-Allow-Origin", "*");
                        String responseMsg = mapper.writeValueAsString(responseMap);
                        res.getWriter().write(responseMsg);
                    });
                })
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
