package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.Order;
import com.example.ecommerce_website.repository.AccountRepository;
import com.example.ecommerce_website.repository.OrderRepository;
import com.example.ecommerce_website.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> getById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getByUsername(String username) {
        return orderRepository.findByUsername(username);
    }

    @Override
    public Order createOrder(Order order) {
        // Kiểm tra user tồn tại
        if (!accountRepository.existsById(order.getUsername())) {
            throw new IllegalArgumentException("Username không tồn tại: " + order.getUsername());
        }
        // Set createDate nếu null
        if (order.getCreateDate() == null) {
            order.setCreateDate(LocalDateTime.now());
        }
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Order order) {
        Order existing = orderRepository.findById(order.getId())
                .orElseThrow(() -> new IllegalArgumentException("Order id không tồn tại: " + order.getId()));
        // Chỉ cập nhật địa chỉ (và các trường khác nếu cần)
        existing.setAddress(order.getAddress());
        return orderRepository.save(existing);
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order id không tồn tại: " + id);
        }
        orderRepository.deleteById(id);
    }
}
