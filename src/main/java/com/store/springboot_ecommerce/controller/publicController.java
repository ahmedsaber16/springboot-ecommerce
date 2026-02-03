package com.store.springboot_ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.springboot_ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.store.springboot_ecommerce.model.Product;

@RestController
@RequestMapping("/api/auth/")
public class publicController {
  
    @Autowired
    private ProductService productService;

    public publicController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchByName(name));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterByPrice(@RequestParam double min , @RequestParam double max) {
        return ResponseEntity.ok(productService.filterByPrice(min, max)) ;
    }
    
    
}