package com.store.springboot_ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.springboot_ecommerce.dto.AuthRes;
import com.store.springboot_ecommerce.dto.LoginReq;
import com.store.springboot_ecommerce.dto.RegisterReq;
import com.store.springboot_ecommerce.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReq req){
        userService.register(req);
        return ResponseEntity.ok("user registered successfuly");

    }


    @PostMapping("/login")
    public ResponseEntity<AuthRes> login(@RequestBody LoginReq req){
        return ResponseEntity.ok(userService.login(req));
    }


}