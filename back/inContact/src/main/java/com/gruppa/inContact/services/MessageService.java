package com.gruppa.inContact.services;

import com.gruppa.inContact.model.Message;
import com.gruppa.inContact.repositories.MessageRepo;
import com.gruppa.inContact.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
        return messageRepo.findById(id).orElseThrow(NotFoundException::new);
    }

    public Message save(Message message) {
        message.setCreationDate(LocalDateTime.now());
        message.setLastUpdateDate(LocalDateTime.now());
        return messageRepo.save(message);
    }

    public Message update(int id, Message message) {
        Message messageFromDb = getMessageById(id);
        messageFromDb.setText(message.getText());
        messageFromDb.setLastUpdateDate(LocalDateTime.now());
        return messageRepo.save(messageFromDb);
    }

    public void delete(int id) {
        messageRepo.delete(getMessageById(id));
    }
}
