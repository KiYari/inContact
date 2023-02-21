package com.gruppa.inContact.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.gruppa.inContact.model.Message;
import com.gruppa.inContact.model.Views;
import com.gruppa.inContact.model.dto.MessageDto;
import com.gruppa.inContact.services.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("message")
@CrossOrigin(value = "*", maxAge = 3600)
public class MessageController {
    private final MessageService messageService;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageController(MessageService messageService, ModelMapper modelMapper) {
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list() {
        return messageService.listAll();
    }

    @GetMapping("{id}")
    public Message getOne(@PathVariable int id) {
        return messageService.getMessageById(id);
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

    private Message mapToMessage(MessageDto dto) {
        return modelMapper.map(dto, Message.class);
    }
}
