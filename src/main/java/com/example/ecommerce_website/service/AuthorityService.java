package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.Authority;

import java.util.List;
import java.util.Optional;

public interface AuthorityService {
    /**
     * Lấy danh sách tất cả Authority
     */
    List<Authority> getAllAuthorities();

    /**
     * Tìm Authority theo id
     */
    Optional<Authority> getById(Integer id);

    /**
     * Tìm tất cả Authority của một user
     */
    List<Authority> getByUsername(String username);

    /**
     * Tạo mới Authority
     * - Kiểm tra tồn tại Account và Role trước khi thêm
     */
    Authority createAuthority(Authority authority);

    /**
     * Cập nhật Authority (ví dụ đổi role của user)
     */
    Authority updateAuthority(Authority authority);

    /**
     * Xóa Authority theo id
     */
    void deleteAuthority(Integer id);
}

