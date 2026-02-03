package com.store.springboot_ecommerce.controller;

import org.springframework.web.bind.annotation.RestController;

import com.store.springboot_ecommerce.model.Product;
import com.store.springboot_ecommerce.service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;






@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
   private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("add/{categoryId}")
    public ResponseEntity<Product> addProduct(@RequestBody Product product ,@PathVariable long categoryId) {
       Product savedProduct = productService.addProduct(product, categoryId);
        return new ResponseEntity<>(savedProduct , HttpStatus.CREATED);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProduct());
    }
    
    @GetMapping("/category/{id}")
    public ResponseEntity<List<Product>> getByCategoryId(@PathVariable("id") long categoryId) {
        return ResponseEntity.ok(productService.getProductbyCategoryId(categoryId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("product is deleted successfully");
    }

    
   



   
}