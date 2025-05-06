package com.example.ecommerce_website.web.rest;

import com.example.ecommerce_website.service.OrderDetailService;
import com.example.ecommerce_website.service.dto.OrderDetailDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/order-details")
@RequiredArgsConstructor
public class OrderDetailAdminController {
    private final OrderDetailService orderDetailService;

    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAll() {
        List<OrderDetailDTO> list = orderDetailService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getById(@PathVariable Long id) {
        Optional<OrderDetailDTO> dto = orderDetailService.findById(id);
        return dto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderDetailDTO>> getByOrder(@PathVariable Long orderId) {
        List<OrderDetailDTO> list = orderDetailService.findByOrderId(orderId);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<OrderDetailDTO> create(@Valid @RequestBody OrderDetailDTO dto) {
        OrderDetailDTO created = orderDetailService.create(dto);
        return ResponseEntity.created(URI.create("/api/admin/order-details/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> update(@PathVariable Long id,
                                                 @Valid @RequestBody OrderDetailDTO dto) {
        OrderDetailDTO updated = orderDetailService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
