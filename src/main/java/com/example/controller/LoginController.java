package com.example.controller;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController
{
    private final AccountRepository accountRepository;

    public LoginController(AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(Account account)
    {
        account = accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(account != null)
        {
            ResponseEntity.ok(account);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
