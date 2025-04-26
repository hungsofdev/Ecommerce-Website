package com.example.ecommerce_website.repository;

import com.example.ecommerce_website.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    List<Authority> findByAccountUsername(String username);

}
