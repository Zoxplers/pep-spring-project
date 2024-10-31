package com.example.controller;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController
{
    private final AccountRepository accountRepository;

    RegisterController(AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }
}
