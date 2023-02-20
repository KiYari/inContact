package com.gruppa.incontact.accounts.controllers;

import com.gruppa.incontact.accounts.model.User;
import com.gruppa.incontact.accounts.model.dto.AuthDto;
import com.gruppa.incontact.accounts.model.dto.UserDto;
import com.gruppa.incontact.accounts.security.JwtTokenUtil;
import com.gruppa.incontact.accounts.services.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    protected final Log logger = LogFactory.getLog(getClass());

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtils;
    private final ModelMapper modelMapper;

    public AuthController(UserService userService, JwtTokenUtil jwtTokenUtils, ModelMapper modelMapper) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<?> loginUser(@RequestBody AuthDto authDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            UserDetails userDetails = userService.loadUserByUsername(authDto.getName());
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    userDetails.getPassword(),
                    userDetails.getAuthorities());
            if(auth.isAuthenticated()) {
                logger.info("Logged in");
                String token = jwtTokenUtils.generateToken(userDetails);
                response.put("error", false);
                response.put("message", "Logged in");
                response.put("token", token);
                return ResponseEntity.ok(response);
            } else {
                response.put("error", true);
                response.put("message", "Invalid credentials");
                return ResponseEntity.status(401).body(response);
            }
        } catch (DisabledException e) {
            e.printStackTrace();
            response.put("error", true);
            response.put("message", "User is disabled");
            return ResponseEntity.status(401).body(response);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            response.put("error", true);
            response.put("message", "Bad credentials");
            return ResponseEntity.status(401).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", true);
            response.put("message", "something went wrong");
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<?> registerNewUser(@RequestBody UserDto userDto) {
        Map<String, Object> response = new HashMap<>();
        userService.createUser(mapToUser(userDto));
        UserDetails userDetails = userService.loadUserByUsername(userDto.getName());
        String token = jwtTokenUtils.generateToken(userDetails);
        response.put("error", false);
        response.put("username", userDto.getName());
        response.put("message", "account created successfully");
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    private User mapToUser(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }
}
