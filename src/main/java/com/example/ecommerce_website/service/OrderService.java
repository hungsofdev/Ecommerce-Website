package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.Order;
import com.example.ecommerce_website.service.dto.OrderDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderDTO create(OrderDTO orderDTO);
    OrderDTO update(Long id, OrderDTO orderDTO);
    void delete(Long id);
    Optional<OrderDTO> findById(Long id);
    List<OrderDTO> findByUsername(String username);
    List<OrderDTO> findAll();
}

