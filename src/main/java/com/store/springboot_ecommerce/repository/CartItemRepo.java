package com.store.springboot_ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.springboot_ecommerce.model.CartItem;
import com.store.springboot_ecommerce.model.Product;
import com.store.springboot_ecommerce.model.User;

import jakarta.transaction.Transactional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem , Long> {

    CartItem findByUserAndProduct( User user , Product product );
    Optional<CartItem> findByUserAndProductId(User user , Long productId);
    List<CartItem> findByUser(User user);
    @Transactional
    void deleteByUserAndProduct_Id(User user , long productId);
}