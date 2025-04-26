package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    /**
     * Lấy danh sách tất cả Product
     */
    List<Product> getAllProducts();

    /**
     * Tìm Product theo id
     */
    Optional<Product> getById(Integer id);

    /**
     * Lấy danh sách Product theo categoryId
     */
    List<Product> getByCategoryId(String categoryId);

    /**
     * Tìm Product theo tên chứa keyword (dùng cho search)
     */
    List<Product> searchByName(String keyword);

    /**
     * Tạo mới Product
     * - Kiểm tra Category tồn tại
     */
    Product createProduct(Product product);

    /**
     * Cập nhật Product
     */
    Product updateProduct(Product product);

    /**
     * Xóa Product theo id
     */
    void deleteProduct(Integer id);
}
