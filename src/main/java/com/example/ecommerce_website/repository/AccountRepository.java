package com.example.ecommerce_website.repository;

import com.example.ecommerce_website.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
