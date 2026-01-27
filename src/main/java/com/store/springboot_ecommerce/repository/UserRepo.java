package com.store.springboot_ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.store.springboot_ecommerce.model.User;

@Repository
public interface UserRepo extends JpaRepository <User, Long>{

    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

    

}
