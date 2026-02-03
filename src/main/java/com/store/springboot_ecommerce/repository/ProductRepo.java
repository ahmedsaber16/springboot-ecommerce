package com.store.springboot_ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.springboot_ecommerce.model.Product;

@Repository
public interface ProductRepo extends JpaRepository< Product , Long> {

    List<Product> findByCategoryCategoryId(long categoryId);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByPriceBetween(double min , double max);



}