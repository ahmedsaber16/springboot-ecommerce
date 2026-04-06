package com.store.springboot_ecommerce.dto;


import java.util.List;
import java.util.stream.Collectors;

import com.store.springboot_ecommerce.model.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id ;
    private String name;
    private String description;
    private List<ProductDto> products ;

    public CategoryDto(Category category) {
        this.id = category.getCategoryId();
        this.name = category.getName();
        this.description = category.getDescription();

        if(category.getProducts() != null){
            this.products = category.getProducts().stream().map(ProductDto::new).collect(Collectors.toList());
        }
    }

}