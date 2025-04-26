package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.Account;
import com.example.ecommerce_website.repository.AccountRepository;
import com.example.ecommerce_website.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> getByUsername(String username) {
        return accountRepository.findById(username);
    }

    @Override
    public Account createAccount(Account account) {
        // Kiểm tra tồn tại
        if (accountRepository.existsById(account.getUsername())) {
            throw new IllegalArgumentException("Username đã tồn tại: " + account.getUsername());
        }
        // Mã hóa mật khẩu
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Account account) {
        // Lấy bản ghi cũ để so sánh
        Account existing = accountRepository.findById(account.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Username không tồn tại: " + account.getUsername()));
        // Nếu mật khẩu mới khác mật khẩu đã mã hóa, mã hóa lại
        if (!passwordEncoder.matches(account.getPassword(), existing.getPassword())) {
            existing.setPassword(passwordEncoder.encode(account.getPassword()));
        }
        // Cập nhật các trường khác
        existing.setFullname(account.getFullname());
        existing.setEmail(account.getEmail());
        existing.setPhoto(account.getPhoto());
        return accountRepository.save(existing);
    }

    @Override
    public void deleteAccount(String username) {
        if (!accountRepository.existsById(username)) {
            throw new IllegalArgumentException("Username không tồn tại: " + username);
        }
        accountRepository.deleteById(username);
    }
}

