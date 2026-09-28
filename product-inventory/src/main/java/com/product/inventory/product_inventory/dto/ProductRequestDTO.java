package com.product.inventory.product_inventory.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductRequestDTO {

    @NotBlank(message = "Product name must not be blank")
    @Size(min = 2, max = 150, message = "Product name must be between 2 and 150 characters")
    private String name;

    @NotBlank(message = "SKU must not be blank")
    @Pattern(regexp = "^[A-Z0-9_-]{3,20}$", message = "SKU must be alphanumeric and between 3 to 20 characters")
    private String sku;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than zero")
    @Digits(integer = 8, fraction = 2, message = "Price format must be up to 8 integer digits and 2 decimals")
    private BigDecimal price;

    @NotNull(message = "Stock quantity is mandatory")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @NotBlank(message = "Category must not be blank")
    private String category;

    public ProductRequestDTO() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}