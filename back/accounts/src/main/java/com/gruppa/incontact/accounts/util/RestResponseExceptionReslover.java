package com.gruppa.incontact.accounts.util;

import com.gruppa.incontact.accounts.util.exceptions.NotYourIdException;
import com.gruppa.incontact.accounts.util.exceptions.UserNotCreatedException;
import com.gruppa.incontact.accounts.util.exceptions.UserNotFoundException;
import com.gruppa.incontact.accounts.util.exceptions.UsernotDeletedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseExceptionReslover extends ResponseEntityExceptionHandler {
    private HttpHeaders headers = new HttpHeaders();

    public RestResponseExceptionReslover() {
        headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    protected ResponseEntity<Object> handleUserNotFoundException(RuntimeException ex) {
        System.out.println(ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("error", true);
        response.put("message", "Cannot find user by this id");
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgumentException(RuntimeException ex) {
        System.out.println(ex.getMessage());
        ex.printStackTrace();
        Map<String, Object> response = new HashMap<>();
        response.put("error", true);
        response.put("message", "Invalid password");
        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler(value = {UserNotCreatedException.class})
    protected ResponseEntity<Object> handleUserNotCreatedException(RuntimeException ex) {
        System.out.println(ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("error", true);
        response.put("message", "User has not been created");
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(value = {UsernotDeletedException.class})
    protected ResponseEntity<Object> handleUserNotDeletedException(RuntimeException ex) {
        System.out.println(ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("error", true);
        response.put("message", "User has not been deleted");
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(value = {NotYourIdException.class})
    protected ResponseEntity<Object> handleNotYourIdException(RuntimeException ex) {
        System.out.println(ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("error", true);
        response.put("message", "Trying to access another user's private data");
        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    protected ResponseEntity<Object> handleAuthenticationException(RuntimeException ex) {
        System.out.println(ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("error", true);
        response.put("message", "Unable to access data. Please consider authentication");
        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    protected ResponseEntity<Object> handleUsernameNotFoundException(RuntimeException ex) {
        System.out.println(ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("error", true);
        response.put("message", "Cannot find user by this name.");
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(value = {DisabledException.class})
    protected ResponseEntity<Object> handleDisabledException (RuntimeException ex) {
        System.out.println(ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("error", true);
        response.put("message", "User is disabled");
        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    protected ResponseEntity<Object> handleBadCredentialsException (RuntimeException ex) {
        System.out.println(ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("error", true);
        response.put("message", "Bad credentials");
        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleUncaughtException(RuntimeException ex) {
        System.out.println(ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("error", true);
        response.put("message", "Something went wrong. Please, consider send logs to support.");
        return ResponseEntity.status(500).body(response);
    }
}
