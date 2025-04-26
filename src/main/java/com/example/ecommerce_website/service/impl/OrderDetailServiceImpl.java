package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.OrderDetail;
import com.example.ecommerce_website.repository.OrderDetailRepository;
import com.example.ecommerce_website.repository.OrderRepository;
import com.example.ecommerce_website.repository.ProductRepository;
import com.example.ecommerce_website.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDetail> getById(Long id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDetail> getByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public OrderDetail createOrderDetail(OrderDetail od) {
        // Kiểm tra Order tồn tại
        if (!orderRepository.existsById(od.getOrderId())) {
            throw new IllegalArgumentException("Order id không tồn tại: " + od.getOrderId());
        }
        // Kiểm tra Product tồn tại
        if (!productRepository.existsById(od.getProductId())) {
            throw new IllegalArgumentException("Product id không tồn tại: " + od.getProductId());
        }
        return orderDetailRepository.save(od);
    }

    @Override
    public OrderDetail updateOrderDetail(OrderDetail od) {
        OrderDetail existing = orderDetailRepository.findById(od.getId())
                .orElseThrow(() -> new IllegalArgumentException("OrderDetail id không tồn tại: " + od.getId()));
        // Cập nhật các trường cần thiết
        existing.setQuantity(od.getQuantity());
        existing.setPrice(od.getPrice());
        return orderDetailRepository.save(existing);
    }

    @Override
    public void deleteOrderDetail(Long id) {
        if (!orderDetailRepository.existsById(id)) {
            throw new IllegalArgumentException("OrderDetail id không tồn tại: " + id);
        }
        orderDetailRepository.deleteById(id);
    }
}

