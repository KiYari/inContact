package com.gruppa.incontact.message.repo;

import com.gruppa.incontact.message.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Integer> {
    public List<Message> findAllByUserId(long userId);
}
