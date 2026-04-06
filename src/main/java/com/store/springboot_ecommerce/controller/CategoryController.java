package com.store.springboot_ecommerce.controller;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.springboot_ecommerce.dto.CategoryDto;
import com.store.springboot_ecommerce.model.Category;

import com.store.springboot_ecommerce.repository.CategoryRepo;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;





import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/admin/categories")
public class CategoryController {


    private final CategoryRepo categoryRepo;

    @PostMapping("/add")
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody Category category) {
        Category savedCategory = categoryRepo.save(category);
        CategoryDto dto = new CategoryDto(savedCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto) ;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categoriesDto = categoryRepo.findAll().stream().map(CategoryDto::new).collect(Collectors.toList()) ;
        return ResponseEntity.ok(categoriesDto) ;
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id){
        categoryRepo.deleteById(id);
        return ResponseEntity.ok("category with id " + id + " has been deleted");

    }

    @GetMapping("{id}")
    public ResponseEntity<Category> getCatogry(@PathVariable long id) {
    Category category = categoryRepo.findById(id)
    .orElseThrow(() -> new RuntimeException("Category not found"));
        return ResponseEntity.ok(category);

    }

}