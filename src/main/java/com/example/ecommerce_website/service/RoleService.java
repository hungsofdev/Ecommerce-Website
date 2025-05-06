package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.Role;
import com.example.ecommerce_website.service.dto.RoleDTO;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    RoleDTO create(RoleDTO roleDTO);
    RoleDTO update(String id, RoleDTO roleDTO);
    void delete(String id);
    Optional<RoleDTO> findById(String id);
    List<RoleDTO> findAll();
}
