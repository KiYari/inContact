package com.gruppa.incontact.message.services;

import com.gruppa.incontact.message.model.Message;
import com.gruppa.incontact.message.repo.MessageRepo;
import com.gruppa.incontact.message.util.exceptions.MessageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepo messageRepo;

    @Autowired
    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public List<Message> listAll() {
        return messageRepo.findAll();
    }

    public Message getMessageById(int id) {
        return messageRepo.findById(id).orElseThrow(MessageNotFoundException::new);
    }

    public Message save(Message message) {
        message.setCreationDate(LocalDateTime.now());
        message.setLastUpdateDate(LocalDateTime.now());
        message.setUserId(message.getUserId());
        messageRepo.saveAndFlush(message);
        return message;
    }

    public Message update(int id, Message message) {
        Message messageFromDb = getMessageById(id);
        messageFromDb.setText(message.getText());
        messageFromDb.setLastUpdateDate(LocalDateTime.now());
        return messageRepo.save(messageFromDb);
    }

    public List<Message> getAllMessagesByUserId(long id) {
        return messageRepo.findAllByUserId(id);
    }

    public void delete(int id) {
        messageRepo.delete(getMessageById(id));
    }
}
