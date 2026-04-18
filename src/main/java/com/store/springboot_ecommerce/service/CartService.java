package com.store.springboot_ecommerce.service;

import java.math.BigDecimal;
import java.util.List;


import org.springframework.stereotype.Service;

import com.store.springboot_ecommerce.dto.CartItemDto;
import com.store.springboot_ecommerce.dto.CartResponseDto;
import com.store.springboot_ecommerce.model.CartItem;
import com.store.springboot_ecommerce.model.Product;
import com.store.springboot_ecommerce.model.User;
import com.store.springboot_ecommerce.repository.CartItemRepo;
import com.store.springboot_ecommerce.repository.ProductRepo;
import com.store.springboot_ecommerce.repository.UserRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepo cartItemRepo;

    private final UserRepo userRepo ;

    private final ProductRepo productRepo;



    // add item to cart
    @Transactional
    public CartItemDto addItemToCart(long productId ,int requestQuantity ,User user ){

        if(user == null  || user.getId() == null || user.getId() == 0){
            throw new RuntimeException("user is not authenticated or userId is missing");
        }
        User managedUser = userRepo.findById(user.getId())
                        .orElseThrow(()-> new RuntimeException("user id not found"));

        Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new RuntimeException("product not found"));

                CartItem existingItem = cartItemRepo.findByUserAndProduct(managedUser, product);

        int currentInCart = (existingItem == null) ? 0 : existingItem.getQuantity();
        int availableToBuy = product.getStock() - currentInCart;

        if(requestQuantity > availableToBuy){
            if(availableToBuy <= 0){
                throw new RuntimeException("unavailable for now.");
            }else{
                throw new RuntimeException("available stock to buy" + availableToBuy);
            }
        }

        CartItem savedItem;

        if( existingItem != null){
            existingItem.setQuantity(currentInCart + requestQuantity);
            savedItem = cartItemRepo.save(existingItem);

        }else{
            CartItem newItem = new CartItem();
            newItem.setUser(managedUser);
            newItem.setProduct(product);
            newItem.setQuantity(requestQuantity);
            savedItem = cartItemRepo.save(newItem);
        }
        return new CartItemDto(savedItem);
    }

    // cartItems
    @Transactional
    public List<CartItemDto> getCartItems(User user){
            return  cartItemRepo.findByUser(user)
            .stream().map(CartItemDto::new).toList();
        }

        @Transactional
    public void deleteItemFromCart(User user ,long productId){
        CartItem cartItem = cartItemRepo.findByUserAndProductId(user, productId)
                    .orElseThrow(()-> new RuntimeException("item not found in your cart"));

        cartItemRepo.delete(cartItem);
    }



    public CartResponseDto getCartResponse(User user){

        List<CartItem> cartItems = cartItemRepo.findByUser(user);

        CartResponseDto cartResponse = new CartResponseDto();
        cartResponse.setUserName(user.getUserName());
        cartResponse.setEmail(user.getEmail());

        List<CartItemDto> itemDtos = cartItems.stream().map(CartItemDto::new).toList();
        cartResponse.setItems(itemDtos);

        BigDecimal total = cartItems.stream()
        .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())) )
        .reduce(BigDecimal.ZERO , BigDecimal::add);

        cartResponse.setTotalCartPrice(total);

        return cartResponse;

    }


    @Transactional
    public CartItemDto updateQuantity(long itemId , int quantity){
        CartItem item = cartItemRepo.findById(itemId)
                        .orElseThrow(() -> new RuntimeException("item not found"));

        if(quantity > item.getProduct().getStock()){
            throw new RuntimeException("unavailable , available for now : " + item.getProduct().getStock());
        }
        item.setQuantity(quantity);
        CartItem updatedItem = cartItemRepo.save(item);
        return new CartItemDto(updatedItem);

    }




}