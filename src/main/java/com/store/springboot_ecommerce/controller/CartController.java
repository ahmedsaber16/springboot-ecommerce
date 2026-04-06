package com.store.springboot_ecommerce.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.springboot_ecommerce.dto.AddToCartReq;
import com.store.springboot_ecommerce.dto.CartItemDto;
import com.store.springboot_ecommerce.dto.CartResponseDto;


import com.store.springboot_ecommerce.model.User;

import com.store.springboot_ecommerce.service.CartService;
import com.store.springboot_ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class CartController {

   private final UserService userService;


   private final CartService cartService ;

   @PostMapping("/add")
   public ResponseEntity<CartItemDto> addToCart(@RequestBody AddToCartReq req){
      User user = userService.getLoggedUser();
      if (user == null || user.getId() == null){
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
      System.out.println("Adding item for User ID: " + user.getId());
      CartItemDto cartItem = cartService.addItemToCart(req.getProductId(), req.getQuantity(), user) ;
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
   public ResponseEntity<CartItemDto> cartItem(@RequestParam long itemId , @RequestParam int quantity){
      CartItemDto updatedItem = cartService.updateQuantity(itemId, quantity);
      return ResponseEntity.ok(updatedItem);
   }





}