package com.example.ecommerce_website.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @Column(name = "Id", length = 10)
    private String id;

    @Column(name = "Name", length = 50, nullable = false)
    private String name;
}
