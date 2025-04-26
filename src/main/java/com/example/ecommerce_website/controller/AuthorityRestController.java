package com.example.ecommerce_website.controller;

import com.example.ecommerce_website.entity.Authority;
import com.example.ecommerce_website.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/authorities")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('STAFF','DIRECTOR')")  // Chỉ STAFF/DIRECTOR được CRUD authority
public class AuthorityRestController {

    private final AuthorityService authorityService;

    @GetMapping
    public ResponseEntity<List<Authority>> listAll() {
        return ResponseEntity.ok(authorityService.getAllAuthorities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Authority> getOne(@PathVariable Integer id) {
        return authorityService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<Authority>> getByUser(@PathVariable String username) {
        List<Authority> list = authorityService.getByUsername(username);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Authority> create(@RequestBody Authority authority) {
        Authority created = authorityService.createAuthority(authority);
        return ResponseEntity
                .created(URI.create("/api/authorities/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Authority> update(@PathVariable Integer id,
                                            @RequestBody Authority authority) {
        if (!id.equals(authority.getId())) {
            return ResponseEntity.badRequest().build();
        }
        Authority updated = authorityService.updateAuthority(authority);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        authorityService.deleteAuthority(id);
        return ResponseEntity.noContent().build();
    }
}

