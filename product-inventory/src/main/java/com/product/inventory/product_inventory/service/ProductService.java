package com.product.inventory.product_inventory.service;

import com.product.inventory.product_inventory.dto.ProductRequestDTO;
import com.product.inventory.product_inventory.dto.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);
    Page getAllProducts(String category, Pageable pageable);
    ProductResponseDTO getProductById(Long id);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO);
    void deleteProduct(Long id);
    List getLowStockAlerts(Integer threshold);
}