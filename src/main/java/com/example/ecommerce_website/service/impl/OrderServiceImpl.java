package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.Account;
import com.example.ecommerce_website.entity.Order;
import com.example.ecommerce_website.entity.OrderDetail;
import com.example.ecommerce_website.entity.Product;
import com.example.ecommerce_website.repository.AccountRepository;
import com.example.ecommerce_website.repository.OrderDetailRepository;
import com.example.ecommerce_website.repository.OrderRepository;
import com.example.ecommerce_website.repository.ProductRepository;
import com.example.ecommerce_website.service.OrderService;
import com.example.ecommerce_website.service.dto.OrderDTO;
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
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDTO> findById(Long id) {
        return orderRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> findByUsername(String username) {
        return orderRepository.findByAccountUsername(username)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDTO create(OrderDTO dto) {
        Account acc = accountRepository.findById(dto.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        Order order = Order.builder()
                .account(acc)
                .createDate(dto.getCreateDate())
                .address(dto.getAddress())
                .build();
        Order saved = orderRepository.save(order);
        // save details
        dto.getDetails().forEach(det -> {
            Product prod = productRepository.findById(det.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            OrderDetail od = OrderDetail.builder()
                    .order(saved)
                    .product(prod)
                    .price(det.getPrice())
                    .quantity(det.getQuantity())
                    .build();
            orderDetailRepository.save(od);
        });
        return mapToDTO(saved);
    }

    @Override
    public OrderDTO update(Long id, OrderDTO dto) {
        // update address only
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setAddress(dto.getAddress());
        Order updated = orderRepository.save(order);
        return mapToDTO(updated);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(Order o) {
        List<OrderDetailDTO> details = orderDetailRepository.findByOrderId(o.getId())
                .stream().map(od -> OrderDetailDTO.builder()
                        .id(od.getId())
                        .productId(od.getProduct().getId())
                        .productName(od.getProduct().getName())
                        .price(od.getPrice())
                        .quantity(od.getQuantity())
                        .build())
                .collect(Collectors.toList());
        return OrderDTO.builder()
                .id(o.getId())
                .username(o.getAccount().getUsername())
                .createDate(o.getCreateDate())
                .address(o.getAddress())
                .details(details)
                .build();
    }
}

