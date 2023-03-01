package com.gruppa.incontact.accounts.controllers;

import com.gruppa.incontact.accounts.model.User;
import com.gruppa.incontact.accounts.model.dto.UserDto;
import com.gruppa.incontact.accounts.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(value = "*", maxAge = 3600)
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/users", consumes = "application/json")
    public HttpStatus createUser(@RequestBody UserDto user) {
        System.out.println("here");
        userService.createUser(mapToUser(user));
        return HttpStatus.OK;
    }

    @GetMapping(value = "/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/user/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PatchMapping(value = "/user/{id}", consumes = "application/json")
    public HttpStatus updateUser (@RequestBody UserDto dto, @PathVariable long id){
        userService.updateUser(dto, id);
        return HttpStatus.OK;
    }

    @DeleteMapping(value = "/user/{id}", consumes = "application/json")
    public HttpStatus deleteUserFromDb(@PathVariable long id) {
        userService.deleteUserById(id);
        return HttpStatus.OK;
    }

    public User mapToUser(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }
}
