package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.Product;
import com.example.ecommerce_website.repository.CategoryRepository;
import com.example.ecommerce_website.repository.ProductRepository;
import com.example.ecommerce_website.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getByCategoryId(String categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public Product createProduct(Product product) {
        // Kiểm tra Category tồn tại
        if (!categoryRepository.existsById(product.getCategoryId())) {
            throw new IllegalArgumentException("Category id không tồn tại: " + product.getCategoryId());
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product existing = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalArgumentException("Product id không tồn tại: " + product.getId()));
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setImage(product.getImage());
        existing.setAvailable(product.getAvailable());
        // Nếu đổi category, kiểm tra tồn tại
        if (!existing.getCategoryId().equals(product.getCategoryId())) {
            if (!categoryRepository.existsById(product.getCategoryId())) {
                throw new IllegalArgumentException("Category id không tồn tại: " + product.getCategoryId());
            }
            existing.setCategoryId(product.getCategoryId());
        }
        return productRepository.save(existing);
    }

    @Override
    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product id không tồn tại: " + id);
        }
        productRepository.deleteById(id);
    }
}

