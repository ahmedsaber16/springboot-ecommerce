package com.store.springboot_ecommerce.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.springboot_ecommerce.model.Product;

@Repository
public interface ProductRepo extends JpaRepository< Product , Long> {

    List<Product> findByCategoryCategoryId(long categoryId);

    Page<Product> findByNameContainingIgnoreCase(String name , Pageable pageable);

    Page<Product> findByPriceBetween(double min , double max ,Pageable pageable);



}