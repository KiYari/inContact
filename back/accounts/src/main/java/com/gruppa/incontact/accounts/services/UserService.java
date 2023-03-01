package com.gruppa.incontact.accounts.services;

import com.gruppa.incontact.accounts.model.Role;
import com.gruppa.incontact.accounts.model.User;
import com.gruppa.incontact.accounts.model.dto.UserDto;
import com.gruppa.incontact.accounts.repo.UserRepo;
import com.gruppa.incontact.accounts.util.exceptions.NotYourIdException;
import com.gruppa.incontact.accounts.util.exceptions.UserNotCreatedException;
import com.gruppa.incontact.accounts.util.exceptions.UserNotFoundException;
import com.gruppa.incontact.accounts.util.exceptions.UsernotDeletedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreatedAt(LocalDateTime.now());
            user.setLastUpdate(LocalDateTime.now());
            user.setLastVisit(LocalDateTime.now());
            user.setUserpic("https://cdn-icons-png.flaticon.com/512/149/149071.png");
            user.setRoles(List.of(Role.USER));
            return repo.save(user);
        } catch (Exception e) {
            throw new UserNotCreatedException();
        }
    }

    public User getUserById(long id) {
        securityIdCheck(id);
        User user = repo.findById(id).orElseThrow(UserNotFoundException::new);
        user.setLastVisit(LocalDateTime.now());
        repo.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User updateUser(UserDto user, long id) {
        securityIdCheck(id);
        User userFromDb = getUserById(id);

        userFromDb.setName(user.getName());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setPassword(passwordEncoder.encode(user.getPassword()));
        userFromDb.setUserpic(user.getUserpic());
        userFromDb.setLastUpdate(LocalDateTime.now());
        userFromDb.setLastVisit(LocalDateTime.now());

        return repo.save(userFromDb);
    }

    public void deleteUserById(long id) {
        securityIdCheck(id);
        try {
            repo.delete(getUserById(id));
        } catch (Exception e) {
            throw new UsernotDeletedException();
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByName(username);
    }

    public void securityIdCheck(long id) {
        User userFromContext = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userFromContext.getAuthorities().contains(Role.ADMIN)) return;
        if(id != userFromContext.getId()) throw new NotYourIdException();
    }

    public long getUserIdByName(String name) {
        return repo.findByName(name).getId();
    }
}
