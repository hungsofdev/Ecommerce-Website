package com.example.ecommerce_website.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "OrderDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "OrderId", nullable = false)
    private Long orderId;

    @Column(name = "ProductId", nullable = false)
    private Integer productId;

    @Column(name = "Price", nullable = false)
    private Double price;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "OrderId", insertable = false, updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ProductId", insertable = false, updatable = false)
    private Product product;
}
