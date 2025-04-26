package com.example.ecommerce_website.repository;

import com.example.ecommerce_website.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryId(String categoryId);
    List<Product> findByNameContainingIgnoreCase(String keyword);

}
