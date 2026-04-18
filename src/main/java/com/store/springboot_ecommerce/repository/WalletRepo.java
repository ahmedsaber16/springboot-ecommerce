package com.store.springboot_ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.store.springboot_ecommerce.model.Wallet;

import jakarta.persistence.LockModeType;
@Repository
public interface WalletRepo extends JpaRepository< Wallet , Long>{
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Wallet> findByUserId(Long userId);
}