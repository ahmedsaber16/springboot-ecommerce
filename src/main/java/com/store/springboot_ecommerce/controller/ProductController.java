package com.store.springboot_ecommerce.controller;

import org.springframework.web.bind.annotation.RestController;

import com.store.springboot_ecommerce.dto.ProductDto;
import com.store.springboot_ecommerce.model.Product;
import com.store.springboot_ecommerce.service.ProductService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;





import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add/{categoryId}")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto product ,@PathVariable Long categoryId) {
        Product savedProduct = productService.addProduct(product, categoryId);
        return new ResponseEntity<>(new ProductDto(savedProduct) , HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDto>> getByCategoryId(@PathVariable("id") long categoryId) {
        return ResponseEntity.ok(productService.getProductbyCategoryId(categoryId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable long id, @RequestBody ProductDto product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("product is deleted successfully");
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