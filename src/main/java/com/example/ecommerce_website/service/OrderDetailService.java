package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    /**
     * Lấy toàn bộ OrderDetail
     */
    List<OrderDetail> getAllOrderDetails();

    /**
     * Tìm OrderDetail theo id
     */
    Optional<OrderDetail> getById(Long id);

    /**
     * Lấy danh sách OrderDetail theo orderId
     */
    List<OrderDetail> getByOrderId(Long orderId);

    /**
     * Tạo mới OrderDetail
     * - Kiểm tra Order và Product tồn tại
     */
    OrderDetail createOrderDetail(OrderDetail orderDetail);

    /**
     * Cập nhật OrderDetail (ví dụ thay đổi số lượng hoặc giá)
     */
    OrderDetail updateOrderDetail(OrderDetail orderDetail);

    /**
     * Xóa OrderDetail theo id
     */
    void deleteOrderDetail(Long id);
}

