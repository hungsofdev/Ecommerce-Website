package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    /**
     * Lấy danh sách tất cả Category
     */
    List<Category> getAllCategories();

    /**
     * Tìm Category theo id
     */
    Optional<Category> getById(String id);

    /**
     * Tạo mới Category
     */
    Category createCategory(Category category);

    /**
     * Cập nhật Category
     */
    Category updateCategory(Category category);

    /**
     * Xóa Category theo id
     */
    void deleteCategory(String id);
}

