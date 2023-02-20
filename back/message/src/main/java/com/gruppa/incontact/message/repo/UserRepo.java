package com.gruppa.incontact.message.repo;

import com.gruppa.incontact.message.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    public User findByName(String name);
}
