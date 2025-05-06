package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.Category;
import com.example.ecommerce_website.entity.Product;
import com.example.ecommerce_website.repository.CategoryRepository;
import com.example.ecommerce_website.service.CategoryService;
import com.example.ecommerce_website.service.dto.CategoryDTO;
import com.example.ecommerce_website.service.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDTO create(CategoryDTO dto) {
        Category entity = Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
        Category saved = categoryRepository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public CategoryDTO update(String id, CategoryDTO dto) {
        Category entity = categoryRepository.findById(id).orElseThrow();
        entity.setName(dto.getName());
        Category updated = categoryRepository.save(entity);
        return mapToDTO(updated);
    }

    @Override
    public void delete(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryDTO> findById(String id) {
        return categoryRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private CategoryDTO mapToDTO(Category category) {
        List<ProductDTO> products = category.getProducts().stream()
                .map(this::mapProductToDTO)
                .collect(Collectors.toList());
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .products(products)
                .build();
    }

    private ProductDTO mapProductToDTO(Product p) {
        return ProductDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .image(p.getImage())
                .price(p.getPrice())
                .createDate(p.getCreateDate())
                .available(p.getAvailable())
                .categoryId(p.getCategory().getId())
                .categoryName(p.getCategory().getName())
                .build();
    }
}
