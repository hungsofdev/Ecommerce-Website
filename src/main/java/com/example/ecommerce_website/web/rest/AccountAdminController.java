
package com.example.ecommerce_website.web.rest;

import com.example.ecommerce_website.service.AccountService;
import com.example.ecommerce_website.service.CategoryService;
import com.example.ecommerce_website.service.AuthorityService;
import com.example.ecommerce_website.service.dto.AccountDTO;
import com.example.ecommerce_website.service.dto.CategoryDTO;
import com.example.ecommerce_website.service.dto.AuthorityDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Accounts (CUSTOMER, STAFF, DIRECTOR).
 */
@RestController
@RequestMapping("/api/admin/accounts")
@RequiredArgsConstructor
public class AccountAdminController {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAll() {
        List<AccountDTO> list = accountService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{username}")
    public ResponseEntity<AccountDTO> getById(@PathVariable String username) {
        Optional<AccountDTO> dto = accountService.findByUsername(username);
        return dto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AccountDTO> create(@Valid @RequestBody AccountDTO dto) {
        AccountDTO created = accountService.register(dto);
        return ResponseEntity.created(URI.create("/api/admin/accounts/" + created.getUsername()))
                .body(created);
    }

    @PutMapping("/{username}")
    public ResponseEntity<AccountDTO> update(@PathVariable String username,
                                             @Valid @RequestBody AccountDTO dto) {
        AccountDTO updated = accountService.update(username, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> delete(@PathVariable String username) {
        accountService.delete(username);
        return ResponseEntity.noContent().build();
    }
}