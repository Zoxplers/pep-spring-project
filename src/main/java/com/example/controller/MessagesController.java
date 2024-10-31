package com.example.controller;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessagesController
{
    private final AccountRepository accountRepository;
    private final MessageRepository messageRepository;

    public MessagesController(AccountRepository accountRepository, MessageRepository messageRepository)
    {
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(Message message)
    {
        if(message.getMessageText().isEmpty() || accountRepository.findAccountByPostedBy(message.getPostedBy()) != null)
        {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(messageRepository.save(message));
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages()
    {
        return ResponseEntity.ok(messageRepository.findAll());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable("messageId") Integer messageId)
    {
        return ResponseEntity.ok(messageRepository.findMessageByMessageId(messageId));
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable("messageId") Integer messageId)
    {
        return ResponseEntity.ok(messageRepository.deleteMessageByMessageId(messageId));
    }
/*
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> patchMessage(@PathVariable("messageId") Integer messageId, Message message)
    {
        if(messageRepository.findMessageByMessageId(messageId) == null || message.getMessageText().isEmpty())
        {
            return ResponseEntity.badRequest().body(null);
        }
        Integer patchedRows = messageRepository.patchMessageByMessageId(messageId, message);
        return patchedRows == 1 ? ResponseEntity.ok(patchedRows) : ResponseEntity.badRequest().body(null);
    }
*/
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> patchMessage(@PathVariable("messageId") Integer messageId, Message message)
    {
        if(messageRepository.findMessageByMessageId(messageId) != null && !message.getMessageText().isEmpty() && messageRepository.patchMessageByMessageId(messageId, message) == 1)
        {
            return ResponseEntity.ok(1);
        }

        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAccountMessages()
    {
        return null;
    }
}