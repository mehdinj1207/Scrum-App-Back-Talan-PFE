package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.model.entity.Messages;
import com.example.ScrumWise.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Date;
@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private MessagesRepository messageRepository;

    /**
     * Sends a message to its destination channel
     *
     * @param message
     */
    @MessageMapping("/messages")
    public void handleMessage(Messages message) {
        System.out.println("*******"+message);
        message.setTimestamp(new Date());
        messageRepository.save(message);
        template.convertAndSend("/channel/chat/" + message.getChannel(), message);
    }
}
