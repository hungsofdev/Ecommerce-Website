package com.example.ecommerce_website.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "Accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(name = "Username", length = 50)
    private String username;

    @Column(name = "Password", length = 50, nullable = false)
    private String password;

    @Column(name = "Fullname", length = 50, nullable = false)
    private String fullname;

    @Column(name = "Email", length = 50, nullable = false)
    private String email;

    @Column(name = "Photo", length = 50)
    private String photo;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Authority> authorities;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Order> orders;
}