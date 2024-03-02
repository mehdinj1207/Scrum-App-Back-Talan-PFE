package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.model.entity.Messages;
import com.example.ScrumWise.model.entity.ReadReceiptRequest;
import com.example.ScrumWise.model.entity.User;
import com.example.ScrumWise.repository.MessagesRepository;
import com.example.ScrumWise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    MessagesRepository messageRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/messages/{channelId}")
    public List<Messages> findMessages(@PathVariable("channelId") String channelId) {
        return messageRepository.findAllByChannel(channelId);
    }

    @PostMapping(value = "/messages")
    public void sendReadReceipt(@RequestBody ReadReceiptRequest request) {
      messageRepository.sendReadReceipt(request.getChannel(), request.getSender(), request.getReceiver());

    }
    @GetMapping("/messages/receivers/{senderEmail}")
    public ResponseEntity<List<User>> getReceiversBySender(@PathVariable String senderEmail) {
        List<String> receiverEmails = messageRepository.findDistinctReceiverBySender(senderEmail);
        List<User> receivers = userRepository.findByEmailIn(receiverEmails);
        return ResponseEntity.ok(receivers);
    }
    @GetMapping("/messages/senders/{receiverEmail}")
    public ResponseEntity<List<User>> getSendersByReceiver(@PathVariable String receiverEmail) {
        List<String> sendersEmails = messageRepository.findDistinctUsers(receiverEmail);
        List<User> senders = userRepository.findByEmailIn(sendersEmails);
        return ResponseEntity.ok(senders);
    }
    @GetMapping("messages/channels/{channel}/lastMessage")
    public ResponseEntity<Messages> getLastMessageByChannel(@PathVariable String channel) {
        Messages lastMessage = messageRepository.findLastMessageByChannel(channel);

        if (lastMessage == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(lastMessage);
    }
}
