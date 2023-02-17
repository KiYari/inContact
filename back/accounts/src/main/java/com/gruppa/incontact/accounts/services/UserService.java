package com.gruppa.incontact.accounts.services;

import com.gruppa.incontact.accounts.model.User;
import com.gruppa.incontact.accounts.model.dto.UserDto;
import com.gruppa.incontact.accounts.repo.UserRepo;
import com.gruppa.incontact.accounts.util.exceptions.UserNotCreatedException;
import com.gruppa.incontact.accounts.util.exceptions.UserNotFoundException;
import com.gruppa.incontact.accounts.util.exceptions.UsernotDeletedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    private final UserRepo repo;

    @Autowired
    public UserService(UserRepo repo) {
        this.repo = repo;
    }

    public User createUser(User user) {
        try {
            System.out.println(user);
            user.setCreatedAt(LocalDateTime.now());
            user.setLastUpdate(LocalDateTime.now());
            user.setLastVisit(LocalDateTime.now());
            return repo.save(user);
        } catch (Exception e) {
            throw new UserNotCreatedException();
        }
    }

    public User getUserById(long id) {
        System.out.println(id);
        User user = repo.findById(id).orElseThrow(UserNotFoundException::new);
        user.setLastVisit(LocalDateTime.now());
        repo.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User updateUser(UserDto user, long id) {
        User userFromDb = getUserById(id);

        userFromDb.setName(user.getName());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setPassword(user.getPassword());
        userFromDb.setUserpic(user.getUserpic());
        userFromDb.setLastUpdate(LocalDateTime.now());
        userFromDb.setLastVisit(LocalDateTime.now());

        return repo.save(userFromDb);
    }

    public void deleteUserById(long id) {
        try {
            repo.delete(getUserById(id));
        } catch (Exception e) {
            throw new UsernotDeletedException();
        }

    }
}
