package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.Authority;
import com.example.ecommerce_website.service.dto.AuthorityDTO;
import com.example.ecommerce_website.service.dto.RoleDTO;

import java.util.List;
import java.util.Optional;

public interface AuthorityService {
    AuthorityDTO assignRole(String username, String roleId);
    AuthorityDTO update(Long id, AuthorityDTO authorityDTO);
    void revoke(Long id);
    Optional<AuthorityDTO> findById(Long id);
    List<AuthorityDTO> findAll();
    List<RoleDTO> findRolesByUsername(String username);
}

