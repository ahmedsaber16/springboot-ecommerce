package com.store.springboot_ecommerce.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;


import com.store.springboot_ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.store.springboot_ecommerce.dto.ProductDto;


@RestController
@RequestMapping("/api/auth/")
public class publicController {

    @Autowired
    private ProductService productService;

    public publicController(ProductService productService) {
        this.productService = productService;
    }
 @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> search(@RequestParam String keyword ,
        @RequestParam(defaultValue = "0") int page ,
        @RequestParam(defaultValue = "10") int size){
            return ResponseEntity.ok(productService.searchByName(keyword, page, size));

    }

    @GetMapping("/filter")
    public ResponseEntity<Page<ProductDto>> filter(@RequestParam double min ,
        @RequestParam double max ,
        @RequestParam(defaultValue = "0") int page ,
        @RequestParam(defaultValue = "10") int size){
    return ResponseEntity.ok(productService.filterByPrice(min, max, page, size));

    }

}