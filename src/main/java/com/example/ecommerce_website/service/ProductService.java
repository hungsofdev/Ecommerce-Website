package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.Product;
import com.example.ecommerce_website.service.dto.ProductDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDTO create(ProductDTO productDTO);
    ProductDTO update(Integer id, ProductDTO productDTO);
    void delete(Integer id);
    Optional<ProductDTO> findById(Integer id);
    Page<ProductDTO> findAll(Pageable pageable);
    List<ProductDTO> findByCategory(String categoryId);
    List<ProductDTO> searchByName(String name);
    List<ProductDTO> searchByPriceRange(Double min, Double max);
    List<ProductDTO> findRelated(Integer productId);
}