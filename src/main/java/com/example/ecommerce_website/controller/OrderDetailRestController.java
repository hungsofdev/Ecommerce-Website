package com.example.ecommerce_website.controller;

import com.example.ecommerce_website.entity.OrderDetail;
import com.example.ecommerce_website.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orderdetails")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('STAFF','DIRECTOR')")
public class OrderDetailRestController {

    private final OrderDetailService orderDetailService;

    @GetMapping
    public ResponseEntity<List<OrderDetail>> listAll() {
        return ResponseEntity.ok(orderDetailService.getAllOrderDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOne(@PathVariable Long id) {
        return orderDetailService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<List<OrderDetail>> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderDetailService.getByOrderId(orderId));
    }

    @PostMapping
    public ResponseEntity<OrderDetail> create(@RequestBody OrderDetail od) {
        OrderDetail created = orderDetailService.createOrderDetail(od);
        return ResponseEntity
                .created(URI.create("/api/orderdetails/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> update(@PathVariable Long id,
                                              @RequestBody OrderDetail od) {
        if (!id.equals(od.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(orderDetailService.updateOrderDetail(od));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.noContent().build();
    }
}

