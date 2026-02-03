package com.store.springboot_ecommerce.dto;

import java.math.BigDecimal;

import com.store.springboot_ecommerce.model.Product;


public class ProductDto {
    
    private long id ;
    private String name ;
    private String description ;
    private BigDecimal price ;
    private int stock;

    
    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
    }
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public int getStock() {
        return stock;
    }

}