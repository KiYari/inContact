package com.gruppa.incontact.message.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.gruppa.incontact.message.model.Message;
import com.gruppa.incontact.message.model.Views;
import com.gruppa.incontact.message.model.dto.MessageDto;
import com.gruppa.incontact.message.services.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class MessageController {
    private final MessageService messageService;
    private final ModelMapper modelMapper;
    private final HttpHeaders headers = new HttpHeaders();

    @Autowired
    public MessageController(MessageService messageService, ModelMapper modelMapper) {
        this.messageService = messageService;
        this.modelMapper = modelMapper;
        headers.add("Access-Control-Allow-Origin", "*");
    }

    @GetMapping("/messages")
    @JsonView(Views.IdName.class)
    public ResponseEntity<?> list() {
        return ResponseEntity.status(200).headers(headers).body(messageService.listAll());
    }

    @GetMapping("{id}")
    public Message getOne(@PathVariable int id) {
        return messageService.getMessageById(id);
    }

    @GetMapping("/message/{id}")
    public ResponseEntity<?> getMessagesOfId(@PathVariable long id) {
        List<Message> messages = messageService.getAllMessagesByUserId(id);
        Map<String, Object> response = new HashMap<>();
        response.put("messages", messages);
        response.put("error", false);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping(consumes = "application/json")
    public Message create(@RequestBody MessageDto dto) {
        return messageService.save(mapToMessage(dto));
    }

    @PutMapping("{id}")
    public Message update(@PathVariable int id, @RequestBody Message message) {
        return messageService.update(id, message);
    }

    @DeleteMapping("{id}")
    public HttpStatus delete(@PathVariable int id) {
        messageService.delete(id);
        return HttpStatus.OK;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message appendMessage(MessageDto message) {
        System.out.println(message.toString());
        return messageService.save(mapToMessage(message));
    }

    private Message mapToMessage(MessageDto dto) {
        return modelMapper.map(dto, Message.class);
    }
}
