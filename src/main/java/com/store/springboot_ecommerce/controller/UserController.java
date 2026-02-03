package com.store.springboot_ecommerce.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.springboot_ecommerce.model.User;
import com.store.springboot_ecommerce.repository.UserRepo;


@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserRepo userRepo ;
    
    @GetMapping("/me")
    public User me(Authentication authentication){
        String email = authentication.getName();

        return userRepo.findByEmail(email).orElseThrow();
    }
}