package com.store.springboot_ecommerce.dto;

import java.math.BigDecimal;

import com.store.springboot_ecommerce.model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private long id ;
    private String name ;
    private String description ;
    private BigDecimal price ;
    private int stock;
    private String categoryName;


    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
        if (product.getCategory() != null){
            this.categoryName = product.getCategory().getName();
        }

    }


}