package com.store.springboot_ecommerce.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.store.springboot_ecommerce.dto.ProductDto;
import com.store.springboot_ecommerce.model.Category;
import com.store.springboot_ecommerce.model.Product;
import com.store.springboot_ecommerce.repository.CategoryRepo;
import com.store.springboot_ecommerce.repository.ProductRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    private final CategoryRepo categoryRepo ;


   //adding products
    public Product addProduct(ProductDto dto , long categoryId){
        Category category = categoryRepo.findById(categoryId)
                            .orElseThrow(() -> new RuntimeException("category not found with id "+ categoryId));

        Product product = new Product();
        product.setName(dto.getName());
        product.setCategory(category);
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());

        return productRepo.save(product);
    }




    //all product
    public List<ProductDto> getAllProduct(){
        return productRepo.findAll().stream().map(ProductDto::new).toList();
    }

    // all product for sepcific category
    public List<ProductDto> getProductbyCategoryId(long categoryId){
        List<Product> products = productRepo.findByCategoryCategoryId(categoryId);
        return products.stream()
        .map(ProductDto::new)
        .toList();
    }

    //delete product

    public void deleteProduct(long id){
        if(!productRepo.existsById(id)){
                throw new RuntimeException("Product not found");
        }
        productRepo.deleteById(id);

    }


    // update product
    public ProductDto updateProduct(long id , ProductDto productDto){
        Product existingProduct  = productRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("product not found"));

        existingProduct.setName(productDto.getName());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setDescription(productDto.getDescription());

        if(productDto.getCategoryName() != null){
            Category category = categoryRepo.findByName(productDto.getCategoryName())
            .orElseThrow(() -> new RuntimeException("Category not found"));
            existingProduct.setCategory(category);
        }
        Product updatedProduct = productRepo.save(existingProduct) ;
        return new ProductDto(updatedProduct);
    }

    // searching
    public Page<ProductDto> searchByName(String keyword , int page , int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Product> products = productRepo.findByNameContainingIgnoreCase(keyword , pageable);
        return products.map(ProductDto::new);
    }

    // filtering
    public Page<ProductDto> filterByPrice(double min , double max , int page , int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("price").ascending());
        Page<Product> products = productRepo.findByPriceBetween(min, max, pageable);
        return products.map(ProductDto::new);
    }



}