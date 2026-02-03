package com.store.springboot_ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.springboot_ecommerce.model.Category;
import com.store.springboot_ecommerce.model.Product;

@Repository
public interface CategoryRepo extends JpaRepository<Category , Long>{

     Optional<Product> findByCategoryId(Long id);
}