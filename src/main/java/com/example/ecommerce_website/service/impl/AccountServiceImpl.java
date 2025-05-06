package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.Account;
import com.example.ecommerce_website.entity.Authority;
import com.example.ecommerce_website.entity.Role;
import com.example.ecommerce_website.repository.AccountRepository;
import com.example.ecommerce_website.repository.AuthorityRepository;
import com.example.ecommerce_website.repository.RoleRepository;
import com.example.ecommerce_website.service.AccountService;
import com.example.ecommerce_website.service.dto.AccountDTO;
import com.example.ecommerce_website.service.dto.AuthorityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;

    @Override
    public AccountDTO register(AccountDTO dto) {
        Account entity = new Account();
        entity.setUsername(dto.getUsername());
        // TODO: set password (to be handled separately)
        entity.setFullname(dto.getFullname());
        entity.setEmail(dto.getEmail());
        entity.setPhoto(dto.getPhoto());
        // save account first
        Account saved = accountRepository.save(entity);
        // assign roles if provided
        if (dto.getRoles() != null) {
            dto.getRoles().forEach(roleId -> {
                Role role = roleRepository.findById(roleId).orElseThrow();
                Authority authority = new Authority();
                authority.setAccount(saved);
                authority.setRole(role);
                authorityRepository.save(authority);
            });
        }
        return mapToDTO(saved);
    }

    @Override
    public AccountDTO update(String username, AccountDTO dto) {
        Account entity = accountRepository.findById(username).orElseThrow();
        entity.setFullname(dto.getFullname());
        entity.setEmail(dto.getEmail());
        entity.setPhoto(dto.getPhoto());
        Account updated = accountRepository.save(entity);
        return mapToDTO(updated);
    }

    @Override
    public void delete(String username) {
        accountRepository.deleteById(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountDTO> findByUsername(String username) {
        return accountRepository.findById(username).map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDTO> findAll() {
        return accountRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private AccountDTO mapToDTO(Account account) {
        List<String> roles = authorityRepository.findByAccountUsername(account.getUsername())
                .stream()
                .map(auth -> auth.getRole().getId())
                .collect(Collectors.toList());
        return AccountDTO.builder()
                .username(account.getUsername())
                .fullname(account.getFullname())
                .email(account.getEmail())
                .photo(account.getPhoto())
                .roles(roles)
                .build();
    }
}