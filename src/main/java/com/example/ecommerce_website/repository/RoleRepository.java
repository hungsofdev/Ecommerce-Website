package com.example.ecommerce_website.repository;

import com.example.ecommerce_website.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
