package com.example.ecommerce_website.web.rest;

import com.example.ecommerce_website.service.AuthorityService;
import com.example.ecommerce_website.service.dto.AuthorityDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/authorities")
@RequiredArgsConstructor
public class AuthorityAdminController {
    private final AuthorityService authorityService;

    @GetMapping
    public ResponseEntity<List<AuthorityDTO>> getAll() {
        List<AuthorityDTO> list = authorityService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorityDTO> getById(@PathVariable Long id) {
        Optional<AuthorityDTO> dto = authorityService.findById(id);
        return dto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AuthorityDTO> create(@Valid @RequestBody AuthorityDTO dto) {
        AuthorityDTO created = authorityService.assignRole(dto.getUsername(), dto.getRoleId());
        return ResponseEntity.created(URI.create("/api/admin/authorities/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorityDTO> update(@PathVariable Long id,
                                               @Valid @RequestBody AuthorityDTO dto) {
        AuthorityDTO updated = authorityService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorityService.revoke(id);
        return ResponseEntity.noContent().build();
    }
}

