package com.example.ecommerce_website.controller;

import com.example.ecommerce_website.entity.Account;
import com.example.ecommerce_website.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('STAFF','DIRECTOR')")  // Chỉ STAFF/DIRECTOR được CRUD account
public class AccountRestController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> listAll() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{username}")
    public ResponseEntity<Account> getOne(@PathVariable String username) {
        return accountService.getByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody Account account) {
        Account created = accountService.createAccount(account);
        // Trả về Location header dẫn tới tài nguyên mới
        return ResponseEntity
                .created(URI.create("/api/accounts/" + created.getUsername()))
                .body(created);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Account> update(@PathVariable String username,
                                          @RequestBody Account account) {
        if (!username.equals(account.getUsername())) {
            return ResponseEntity.badRequest().build();
        }
        Account updated = accountService.updateAccount(account);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> delete(@PathVariable String username) {
        accountService.deleteAccount(username);
        return ResponseEntity.noContent().build();
    }
}

