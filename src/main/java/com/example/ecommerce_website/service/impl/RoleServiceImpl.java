package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.Role;
import com.example.ecommerce_website.repository.RoleRepository;
import com.example.ecommerce_website.service.RoleService;
import com.example.ecommerce_website.service.dto.RoleDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public RoleDTO create(RoleDTO dto) {
        Role role = Role.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
        Role saved = roleRepository.save(role);
        return mapToDTO(saved);
    }

    @Override
    public RoleDTO update(String id, RoleDTO dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        role.setName(dto.getName());
        Role updated = roleRepository.save(role);
        return mapToDTO(updated);
    }

    @Override
    public void delete(String id) {
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleDTO> findById(String id) {
        return roleRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> findAll() {
        return roleRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private RoleDTO mapToDTO(Role r) {
        return RoleDTO.builder()
                .id(r.getId())
                .name(r.getName())
                .build();
    }
}
