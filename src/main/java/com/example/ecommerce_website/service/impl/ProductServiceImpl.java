package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.*;
import com.example.ecommerce_website.repository.*;
import com.example.ecommerce_website.service.*;
import com.example.ecommerce_website.service.dto.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ServiceImpl cho Product
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> findByCategory(String categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findById(Integer id) {
        return productRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> searchByPriceRange(Double min, Double max) {
        return productRepository.findByPriceBetween(min, max)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> findRelated(Integer productId) {
        Product p = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return productRepository.findByCategoryId(p.getCategory().getId())
                .stream().filter(prod -> !prod.getId().equals(productId))
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO create(ProductDTO dto) {
        Category cat = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        Product entity = Product.builder()
                .name(dto.getName())
                .image(dto.getImage())
                .price(dto.getPrice())
                .createDate(dto.getCreateDate())
                .available(dto.getAvailable())
                .category(cat)
                .build();
        Product saved = productRepository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public ProductDTO update(Integer id, ProductDTO dto) {
        Product entity = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        entity.setName(dto.getName());
        entity.setImage(dto.getImage());
        entity.setPrice(dto.getPrice());
        entity.setAvailable(dto.getAvailable());
        // createDate unchanged
        Product updated = productRepository.save(entity);
        return mapToDTO(updated);
    }

    @Override
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }

    private ProductDTO mapToDTO(Product p) {
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