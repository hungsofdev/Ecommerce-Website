package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.Account;
import com.example.ecommerce_website.service.dto.AccountDTO;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    AccountDTO register(AccountDTO accountDTO);
    AccountDTO update(String username, AccountDTO accountDTO);
    void delete(String username);
    Optional<AccountDTO> findByUsername(String username);
    List<AccountDTO> findAll();
}

