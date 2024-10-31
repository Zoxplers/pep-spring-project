package com.example.controller;


import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController
{
    private final AccountRepository accountRepository;
    private final MessageRepository messageRepository;

    public SocialMediaController(AccountRepository accountRepository, MessageRepository messageRepository)
    {
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account)
    {
        if(accountRepository.findAccountByUsername(account.getUsername()) != null)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        try
        {
            accountRepository.save(account);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(account);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account)
    {
        account = accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(account != null)
        {
            ResponseEntity.ok(account);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message)
    {
        if(message.getMessageText().isEmpty() || accountRepository.findAccountByAccountId(message.getPostedBy()) != null)
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

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> patchMessage(@PathVariable("messageId") Integer messageId, @RequestBody Message newMessage)
    {
        Message message = messageRepository.findMessageByMessageId(messageId);
        if(message != null && !newMessage.getMessageText().isEmpty())
        {
            message.setMessageText(newMessage.getMessageText());
            messageRepository.save(message);
            return ResponseEntity.ok(1);
        }

        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAccountMessages(@PathVariable("accountId") Integer accountId)
    {
        return ResponseEntity.ok(messageRepository.findMessagesByPostedBy(accountId));
    }
}