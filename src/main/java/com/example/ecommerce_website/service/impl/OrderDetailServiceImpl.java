package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.Order;
import com.example.ecommerce_website.entity.OrderDetail;
import com.example.ecommerce_website.entity.Product;
import com.example.ecommerce_website.repository.OrderDetailRepository;
import com.example.ecommerce_website.repository.OrderRepository;
import com.example.ecommerce_website.repository.ProductRepository;
import com.example.ecommerce_website.service.OrderDetailService;
import com.example.ecommerce_website.service.dto.OrderDetailDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDetailDTO> findById(Long id) {
        return orderDetailRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDetailDTO> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDetailDTO> findAll() {
        return orderDetailRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDetailDTO create(OrderDetailDTO dto) {
        Order o = orderRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        Product p = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        OrderDetail od = OrderDetail.builder()
                .order(o)
                .product(p)
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .build();
        OrderDetail saved = orderDetailRepository.save(od);
        return mapToDTO(saved);
    }

    @Override
    public OrderDetailDTO update(Long id, OrderDetailDTO dto) {
        OrderDetail od = orderDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderDetail not found"));
        od.setQuantity(dto.getQuantity());
        od.setPrice(dto.getPrice());
        OrderDetail updated = orderDetailRepository.save(od);
        return mapToDTO(updated);
    }

    @Override
    public void delete(Long id) {
        orderDetailRepository.deleteById(id);
    }

    private OrderDetailDTO mapToDTO(OrderDetail od) {
        return OrderDetailDTO.builder()
                .id(od.getId())
                .productId(od.getProduct().getId())
                .productName(od.getProduct().getName())
                .price(od.getPrice())
                .quantity(od.getQuantity())
                .build();
    }
}
