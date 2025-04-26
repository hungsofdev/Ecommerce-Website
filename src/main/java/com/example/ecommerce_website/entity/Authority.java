package com.example.ecommerce_website.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Authorities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "RoleId", length = 10, nullable = false)
    private String roleId;

    @ManyToOne
    @JoinColumn(name = "Username", referencedColumnName = "Username")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "RoleId", insertable = false, updatable = false)
    private Role role;
}