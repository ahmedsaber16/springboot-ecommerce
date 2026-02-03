package com.store.springboot_ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.springboot_ecommerce.model.OrderItems;

@Repository
public interface OrderItemsRepo extends JpaRepository<OrderItems , Long> {
}