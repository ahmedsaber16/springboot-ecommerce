package com.store.springboot_ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.springboot_ecommerce.model.Payment;
@Repository
public interface PaymentRepo extends JpaRepository<Payment , Long> {
}