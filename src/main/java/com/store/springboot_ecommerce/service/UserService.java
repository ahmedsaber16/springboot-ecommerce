package com.store.springboot_ecommerce.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.store.springboot_ecommerce.dto.AuthRes;
import com.store.springboot_ecommerce.dto.LoginReq;
import com.store.springboot_ecommerce.dto.RegisterReq;
import com.store.springboot_ecommerce.model.User;
import com.store.springboot_ecommerce.repository.UserRepo;

@Service
public class UserService {
    private UserRepo userRepo ;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

     public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder , JwtService jwtService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService ;
    }


    public void register(RegisterReq req){

        boolean exists = userRepo.findByEmail(req.getEmail()).isPresent();

        if(exists){
            throw new RuntimeException("Email already exist");
        }    
        User user = new User();
        user.setEmail(req.getEmail());
        user.setUserName(req.getUserName());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole("USER");
        userRepo.save(user);

    }

    public AuthRes login(LoginReq req){
        User user = userRepo.findByEmail(req.getEmail())
        .orElseThrow(()-> new RuntimeException("invalid credential"));

        if(!passwordEncoder.matches(req.getPassword(), user.getPassword())){
            throw new RuntimeException("invalid password");
        }

        String token = jwtService.generateToken(user);

        return new AuthRes(token, user.getUserName(), user.getRole());

    }


    public User getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        System.out.println("Username from Token: " + email);
        return userRepo.findByEmail(email)
                       .orElseThrow(() -> new RuntimeException("user not found"));
    }
   

    
    
}