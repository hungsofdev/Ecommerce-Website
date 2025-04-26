package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.Category;
import com.example.ecommerce_website.repository.CategoryRepository;
import com.example.ecommerce_website.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> getById(String id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category createCategory(Category category) {
        if (categoryRepository.existsById(category.getId())) {
            throw new IllegalArgumentException("Category id đã tồn tại: " + category.getId());
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        Category existing = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new IllegalArgumentException("Category id không tồn tại: " + category.getId()));
        existing.setName(category.getName());
        return categoryRepository.save(existing);
    }

    @Override
    public void deleteCategory(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category id không tồn tại: " + id);
        }
        categoryRepository.deleteById(id);
    }
}

