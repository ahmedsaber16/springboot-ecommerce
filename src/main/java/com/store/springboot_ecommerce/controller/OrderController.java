package com.store.springboot_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.springboot_ecommerce.dto.OrderResponseDto;

import com.store.springboot_ecommerce.model.User;
import com.store.springboot_ecommerce.service.OrderService;
import com.store.springboot_ecommerce.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/orders")
public class OrderController {
   @Autowired
   private UserService userService ;

   @Autowired
   private OrderService orderService;

   @PostMapping("/checkout")
   public ResponseEntity<OrderResponseDto> checkout() {
        User user = userService.getLoggedUser();
        OrderResponseDto responseDto = orderService.placeOrder(user);
       
       return new ResponseEntity<>(responseDto , HttpStatus.CREATED);
   }
   
}