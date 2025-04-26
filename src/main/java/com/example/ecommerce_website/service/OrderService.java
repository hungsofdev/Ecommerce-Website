package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    /**
     * Lấy toàn bộ Orders
     */
    List<Order> getAllOrders();

    /**
     * Tìm Order theo id
     */
    Optional<Order> getById(Long id);

    /**
     * Lấy danh sách Orders của một user
     */
    List<Order> getByUsername(String username);

    /**
     * Tạo mới Order
     * - Kiểm tra Account tồn tại
     * - Thiết lập createDate nếu chưa có
     */
    Order createOrder(Order order);

    /**
     * Cập nhật Order (ví dụ thay đổi địa chỉ)
     */
    Order updateOrder(Order order);

    /**
     * Xóa Order theo id
     */
    void deleteOrder(Long id);
}

