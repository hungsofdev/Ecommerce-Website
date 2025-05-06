package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.OrderDetail;
import com.example.ecommerce_website.service.dto.OrderDetailDTO;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    OrderDetailDTO create(OrderDetailDTO orderDetailDTO);
    OrderDetailDTO update(Long id, OrderDetailDTO orderDetailDTO);
    void delete(Long id);
    Optional<OrderDetailDTO> findById(Long id);
    List<OrderDetailDTO> findByOrderId(Long orderId);
    List<OrderDetailDTO> findAll();
}
