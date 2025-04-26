package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.Authority;
import com.example.ecommerce_website.repository.AuthorityRepository;
import com.example.ecommerce_website.repository.AccountRepository;
import com.example.ecommerce_website.repository.RoleRepository;
import com.example.ecommerce_website.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Authority> getAllAuthorities() {
        return authorityRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Authority> getById(Integer id) {
        return authorityRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Authority> getByUsername(String username) {
        return authorityRepository.findByAccountUsername(username);
    }

    @Override
    public Authority createAuthority(Authority authority) {
        // Kiểm tra Account tồn tại
        if (!accountRepository.existsById(authority.getAccount().getUsername())) {
            throw new IllegalArgumentException("Username không tồn tại: " + authority.getAccount().getUsername());
        }
        // Kiểm tra Role tồn tại
        if (!roleRepository.existsById(authority.getRoleId())) {
            throw new IllegalArgumentException("RoleId không tồn tại: " + authority.getRoleId());
        }
        return authorityRepository.save(authority);
    }

    @Override
    public Authority updateAuthority(Authority authority) {
        Authority existing = authorityRepository.findById(authority.getId())
                .orElseThrow(() -> new IllegalArgumentException("Authority id không tồn tại: " + authority.getId()));
        // Chỉ cập nhật role nếu khác
        if (!existing.getRoleId().equals(authority.getRoleId())) {
            if (!roleRepository.existsById(authority.getRoleId())) {
                throw new IllegalArgumentException("RoleId không tồn tại: " + authority.getRoleId());
            }
            existing.setRoleId(authority.getRoleId());
        }
        // Username thường không đổi; nếu đổi cũng cần check Account tồn tại
        return authorityRepository.save(existing);
    }

    @Override
    public void deleteAuthority(Integer id) {
        if (!authorityRepository.existsById(id)) {
            throw new IllegalArgumentException("Authority id không tồn tại: " + id);
        }
        authorityRepository.deleteById(id);
    }
}
