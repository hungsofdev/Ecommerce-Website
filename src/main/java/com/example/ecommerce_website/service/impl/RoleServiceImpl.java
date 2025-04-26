package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.Role;
import com.example.ecommerce_website.repository.RoleRepository;
import com.example.ecommerce_website.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> getById(String id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role createRole(Role role) {
        if (roleRepository.existsById(role.getId())) {
            throw new IllegalArgumentException("Role id đã tồn tại: " + role.getId());
        }
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Role role) {
        Role existing = roleRepository.findById(role.getId())
                .orElseThrow(() -> new IllegalArgumentException("Role id không tồn tại: " + role.getId()));
        existing.setName(role.getName());
        return roleRepository.save(existing);
    }

    @Override
    public void deleteRole(String id) {
        if (!roleRepository.existsById(id)) {
            throw new IllegalArgumentException("Role id không tồn tại: " + id);
        }
        roleRepository.deleteById(id);
    }
}
