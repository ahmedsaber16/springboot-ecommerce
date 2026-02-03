package com.store.springboot_ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.springboot_ecommerce.dto.CartItemDto;
import com.store.springboot_ecommerce.dto.CartResponseDto;
import com.store.springboot_ecommerce.model.CartItem;
import com.store.springboot_ecommerce.model.Product;
import com.store.springboot_ecommerce.model.User;
import com.store.springboot_ecommerce.repository.CartItemRepo;
import com.store.springboot_ecommerce.repository.ProductRepo;

import jakarta.transaction.Transactional;

@Service
public class CartService {

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private ProductRepo productRepo;

   

  

    @Transactional
    public CartItem addItemToCart(long productId ,int requestQuantity ,User user ){

        Product product = productRepo.findById(productId)
                          .orElseThrow(() -> new RuntimeException("product not found"));

        CartItem existingItem = cartItemRepo.findByUserAndProduct(user, product);

        int currentInCart = (existingItem == null) ? 0 : existingItem.getQuantity();
        int availableToBuy = product.getStock() - currentInCart;

        if(requestQuantity > availableToBuy){
            if(availableToBuy <= 0){
                throw new RuntimeException("unavailable for now.");
            }else{
                throw new RuntimeException("available stock to buy" + availableToBuy);
            }
        }

        
        if( existingItem != null){
             existingItem.setQuantity(currentInCart + requestQuantity);
             return cartItemRepo.save(existingItem);
        }else{
            CartItem newItem = new CartItem( user , product , requestQuantity);
            return cartItemRepo.save(newItem);
        }
    }

    public List<CartItem> getCartItems(User user){
        return cartItemRepo.findByUser(user);
        }

        @Transactional
    public void deleteItemFromCart(User user ,long productId){

        cartItemRepo.deleteByUserAndProduct_Id(user , productId);
    }



    public CartResponseDto getCartResponse(User user){

        List<CartItem> cartItems = cartItemRepo.findByUser(user);

        CartResponseDto cartResponse = new CartResponseDto();
        cartResponse.setUserName(user.getUserName());
        cartResponse.setEmail(user.getEmail());
        
        List<CartItemDto> itemDtos = cartItems.stream().map(item -> {

            CartItemDto dto = new CartItemDto();
            dto.setName(item.getProduct().getName());
            dto.setPrice(item.getProduct().getPrice().doubleValue());
            dto.setQuantity(item.getQuantity());
            dto.setProductId(item.getProduct().getId());
            return dto;
        }).collect(Collectors.toList());
        cartResponse.setItems(itemDtos);

        double total = cartItems.stream()
        .mapToDouble(item -> item.getProduct().getPrice().doubleValue() * item.getQuantity())
        .sum();

        cartResponse.setTotalCartPrice(total);

        return cartResponse;

    }


    @Transactional
    public CartItem updateQuantity(long itemId , int quantity){
        CartItem item = cartItemRepo.findById(itemId)
                        .orElseThrow(() -> new RuntimeException("item not found"));
        
        if(quantity > item.getProduct().getStock()){
            throw new RuntimeException("unavailable , available for now : " + item.getProduct().getStock());
        }
        item.setQuantity(quantity);
        return cartItemRepo.save(item);

    }




}