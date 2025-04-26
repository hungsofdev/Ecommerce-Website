package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    /**
     * Lấy tất cả Role
     */
    List<Role> getAllRoles();

    /**
     * Tìm Role theo id
     */
    Optional<Role> getById(String id);

    /**
     * Tạo mới Role
     */
    Role createRole(Role role);

    /**
     * Cập nhật Role
     */
    Role updateRole(Role role);

    /**
     * Xóa Role theo id
     */
    void deleteRole(String id);
}
