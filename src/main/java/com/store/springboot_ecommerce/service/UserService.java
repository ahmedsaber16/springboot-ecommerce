package com.store.springboot_ecommerce.service;


import java.math.BigDecimal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.store.springboot_ecommerce.dto.AuthRes;
import com.store.springboot_ecommerce.dto.LoginReq;
import com.store.springboot_ecommerce.dto.RegisterReq;
import com.store.springboot_ecommerce.model.User;
import com.store.springboot_ecommerce.model.Wallet;
import com.store.springboot_ecommerce.repository.UserRepo;
import com.store.springboot_ecommerce.repository.WalletRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo ;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final WalletRepo walletRepo;




    @Transactional
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

        User savedUser = userRepo.save(user);

        Wallet wallet = new Wallet();
        wallet.setUser(savedUser);
        wallet.setBalance(new BigDecimal("1000.00"));
        wallet.setVersion(0L);
        walletRepo.save(wallet);
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