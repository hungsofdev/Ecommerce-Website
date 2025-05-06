package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.Category;
import com.example.ecommerce_website.service.dto.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryDTO create(CategoryDTO categoryDTO);
    CategoryDTO update(String id, CategoryDTO categoryDTO);
    void delete(String id);
    Optional<CategoryDTO> findById(String id);
    List<CategoryDTO> findAll();
}

