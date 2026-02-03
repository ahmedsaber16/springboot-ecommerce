package com.store.springboot_ecommerce.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.springboot_ecommerce.dto.CartResponseDto;
import com.store.springboot_ecommerce.model.CartItem;

import com.store.springboot_ecommerce.model.User;

import com.store.springboot_ecommerce.service.CartService;
import com.store.springboot_ecommerce.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

   @Autowired
   private UserService userService;

   @Autowired
   private CartService cartService ;

   @PostMapping("/add")
   public ResponseEntity<CartItem> addToCart(@RequestParam long productId , @RequestParam int quantity){
    User user = userService.getLoggedUser();
    CartItem cartItem = cartService.addItemToCart(productId, quantity, user) ;
    return ResponseEntity.ok(cartItem);
   }


   @GetMapping("/mycart")
   public ResponseEntity<CartResponseDto> getMyCarts(){
    User user = userService.getLoggedUser();
    CartResponseDto response = cartService.getCartResponse(user);
    return ResponseEntity.ok(response);
   }


   @DeleteMapping("/delete/{id}")
   public ResponseEntity<String> deleteItem(@PathVariable("id") long productId){
      User user = userService.getLoggedUser();
      cartService.deleteItemFromCart(user, productId);
      return ResponseEntity.ok("item deleted successfully");
   }

   @PutMapping("/update") // update quantity
   public ResponseEntity<CartItem> cartItem(@RequestParam long itemId , @RequestParam int quantity){
      CartItem updatedItem = cartService.updateQuantity(itemId, quantity);
      return ResponseEntity.ok(updatedItem);
   }





}