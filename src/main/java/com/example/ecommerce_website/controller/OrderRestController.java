package com.example.ecommerce_website.controller;

import com.example.ecommerce_website.entity.Order;
import com.example.ecommerce_website.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('STAFF','DIRECTOR')")
public class OrderRestController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> listAll() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOne(@PathVariable Long id) {
        return orderService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Danh sách đơn hàng của 1 user.
     * Staff/Director cũng có thể xem,
     * hoặc bạn có thể move endpoint này sang 1 controller khác cho khách hàng.
     */
    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<Order>> getByUser(@PathVariable String username) {
        return ResponseEntity.ok(orderService.getByUsername(username));
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        Order created = orderService.createOrder(order);
        return ResponseEntity
                .created(URI.create("/api/orders/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id,
                                        @RequestBody Order order) {
        if (!id.equals(order.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(orderService.updateOrder(order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
