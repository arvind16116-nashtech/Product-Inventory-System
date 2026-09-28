package com.product.inventory.product_inventory.service.impl;

import com.product.inventory.product_inventory.dto.ProductRequestDTO;
import com.product.inventory.product_inventory.dto.ProductResponseDTO;
import com.product.inventory.product_inventory.entity.Product;
import com.product.inventory.product_inventory.exception.DuplicateResourceException;
import com.product.inventory.product_inventory.exception.ResourceNotFoundException;
import com.product.inventory.product_inventory.repository.ProductRepository;
import com.product.inventory.product_inventory.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        if (productRepository.existsBySku(requestDTO.getSku())) {
            throw new DuplicateResourceException("Product with SKU '" + requestDTO.getSku() + "' already exists.");
        }
        Product product = mapToEntity(requestDTO);
        Product savedProduct = productRepository.save(product);
        return mapToDTO(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public Page getAllProducts(String category, Pageable pageable) {
        Page<Product> productsPage;
        if (category != null && !category.trim().isEmpty()) {
            productsPage = productRepository.findByCategoryIgnoreCase(category, pageable);
        } else {
            productsPage = productRepository.findAll(pageable);
        }
        return productsPage.map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return mapToDTO(product);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (!existingProduct.getSku().equalsIgnoreCase(requestDTO.getSku()) 
                && productRepository.existsBySku(requestDTO.getSku())) {
            throw new DuplicateResourceException("Product with SKU '" + requestDTO.getSku() + "' already exists.");
        }

        existingProduct.setName(requestDTO.getName());
        existingProduct.setSku(requestDTO.getSku());
        existingProduct.setPrice(requestDTO.getPrice());
        existingProduct.setStockQuantity(requestDTO.getStockQuantity());
        existingProduct.setCategory(requestDTO.getCategory());

        Product updatedProduct = productRepository.save(existingProduct);
        return mapToDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List getLowStockAlerts(Integer threshold) {
        int limit = (threshold != null) ? threshold : 10;
        return productRepository.findLowStockProducts(limit).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private Product mapToEntity(ProductRequestDTO dto) {
        Product entity = new Product();
        entity.setName(dto.getName());
        entity.setSku(dto.getSku());
        entity.setPrice(dto.getPrice());
        entity.setStockQuantity(dto.getStockQuantity());
        entity.setCategory(dto.getCategory());
        return entity;
    }

    private ProductResponseDTO mapToDTO(Product entity) {
        return new ProductResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getSku(),
                entity.getPrice(),
                entity.getStockQuantity(),
                entity.getCategory()
        );
    }
}