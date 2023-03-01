package com.gruppa.incontact.accounts.util;

import com.gruppa.incontact.accounts.util.exceptions.NotYourIdException;
import com.gruppa.incontact.accounts.util.exceptions.UserNotCreatedException;
import com.gruppa.incontact.accounts.util.exceptions.UserNotFoundException;
import com.gruppa.incontact.accounts.util.exceptions.UsernotDeletedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class RestResponseExceptionReslover extends ResponseEntityExceptionHandler {
    private HttpHeaders headers = new HttpHeaders();

    public RestResponseExceptionReslover() {
        headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    protected ResponseEntity<Object> handleUserNotFoundException(RuntimeException ex, WebRequest request) {
        System.out.println(ex.getMessage());
        String responseBody = "User with such id has not been found.";
        return handleExceptionInternal(ex, responseBody, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {UserNotCreatedException.class})
    protected ResponseEntity<Object> handleUserNotCreatedException(RuntimeException ex, WebRequest request) {
        System.out.println(ex.getMessage());
        String responseBody = "User has not been created.";
        return handleExceptionInternal(ex, responseBody, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {UsernotDeletedException.class})
    protected ResponseEntity<Object> handleUserNotDeletedException(RuntimeException ex, WebRequest request) {
        System.out.println(ex.getMessage());
        String responseBody = "User has not been deleted.";
        return handleExceptionInternal(ex, responseBody, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {NotYourIdException.class})
    protected ResponseEntity<Object> handleNotYourIdException(RuntimeException ex, WebRequest request) {
        System.out.println(ex.getMessage());
        String responseBody = "Trying to access for private data.";
        return handleExceptionInternal(ex, responseBody, headers, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    protected ResponseEntity<Object> handleAuthenticationException(RuntimeException ex, WebRequest request) {
        System.out.println(ex.getMessage());
        String responseBody = "Unable to access data. Unauthenticated pequest, please consider authentication.";
        return handleExceptionInternal(ex, responseBody, headers, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    protected ResponseEntity<Object> handleUsernameNotFoundException(RuntimeException ex, WebRequest request) {
        System.out.println(ex.getMessage());
        String responseBody = ex.getMessage();
        return handleExceptionInternal(ex, responseBody, headers, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleUncaughtException(RuntimeException ex, WebRequest request) {
        System.out.println(ex.getMessage());
        String responseBody = "Something went wrong on server side.";
        return handleExceptionInternal(ex, responseBody, headers, HttpStatus.BAD_REQUEST, request);
    }
}
