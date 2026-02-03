package com.store.springboot_ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.springboot_ecommerce.model.Order;
import com.store.springboot_ecommerce.model.User;

@Repository
public interface OrderRepo  extends JpaRepository<Order , Long>{
    List<Order> findByUser(User user); 
}