package com.store.springboot_ecommerce.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.store.springboot_ecommerce.model.Category;



public class CategoryDto {
   
    
    private Long id ;
    private String name;
    private String description;         
    private List<ProductDto> products ;


    public CategoryDto(Category category) {
        this.id = category.getCategoryId();
        this.name = category.getName();
        this.description = category.getDescription();
        if (category.getProducts() != null ){
           this.products = category.getProducts().stream().map(ProductDto::new).collect(Collectors.toList()); 
        }
        else{
            this.products = new ArrayList<>() ;
        }
    }


    public Long getCategoryId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public List<ProductDto> getProducts() {
        return products;
    } 



}