package com.gruppa.incontact.message.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppa.incontact.message.config.filters.JwtRequestFilter;
import com.gruppa.incontact.message.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
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
                .authorizeHttpRequests(req -> {
                        req
                                .requestMatchers("/ws/**").permitAll()
                                .anyRequest().authenticated();
                    }
                )
                .cors()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((req, res, authException) -> {
                    Map<String, Object> responseMap = new HashMap<>();
                    ObjectMapper mapper = new ObjectMapper();
                    res.setStatus(404);
                    responseMap.put("error", true);
                    responseMap.put("message", "Unexpected request. Please, try another");
                    res.setHeader("Content-Type", "application/json");
                    res.setHeader("Access-Control-Allow-Origin", "*");
                    String responseMsg = mapper.writeValueAsString(responseMap);
                    res.getWriter().write(responseMsg);
                })
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
