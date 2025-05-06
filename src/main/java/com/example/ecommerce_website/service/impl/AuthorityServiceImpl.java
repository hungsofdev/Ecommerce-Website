package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.Account;
import com.example.ecommerce_website.entity.Authority;
import com.example.ecommerce_website.entity.Role;
import com.example.ecommerce_website.repository.AccountRepository;
import com.example.ecommerce_website.repository.AuthorityRepository;
import com.example.ecommerce_website.repository.RoleRepository;
import com.example.ecommerce_website.service.AuthorityService;
import com.example.ecommerce_website.service.dto.AuthorityDTO;
import com.example.ecommerce_website.service.dto.RoleDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;

    @Override
    public AuthorityDTO assignRole(String username, String roleId) {
        Account account = accountRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        Authority auth = new Authority();
        auth.setAccount(account);
        auth.setRole(role);
        Authority saved = authorityRepository.save(auth);
        return mapToDTO(saved);
    }

    @Override
    public AuthorityDTO update(Long id, AuthorityDTO dto) {
        Authority auth = authorityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Authority not found"));
        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        auth.setRole(role);
        Authority updated = authorityRepository.save(auth);
        return mapToDTO(updated);
    }

    @Override
    public void revoke(Long id) {
        authorityRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorityDTO> findById(Long id) {
        return authorityRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorityDTO> findAll() {
        return authorityRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> findRolesByUsername(String username) {
        return authorityRepository.findByAccountUsername(username)
                .stream()
                .map(auth -> RoleDTO.builder()
                        .id(auth.getRole().getId())
                        .name(auth.getRole().getName())
                        .build())
                .collect(Collectors.toList());
    }

    private AuthorityDTO mapToDTO(Authority auth) {
        return AuthorityDTO.builder()
                .id(auth.getId())
                .username(auth.getAccount().getUsername())
                .roleId(auth.getRole().getId())
                .roleName(auth.getRole().getName())
                .build();
    }
}
