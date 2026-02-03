package com.store.springboot_ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.springboot_ecommerce.model.Order;
import com.store.springboot_ecommerce.model.OrderItems;
import com.store.springboot_ecommerce.model.Product;
import com.store.springboot_ecommerce.dto.OrderItemDto;
import com.store.springboot_ecommerce.dto.OrderResponseDto;
import com.store.springboot_ecommerce.model.CartItem;
import com.store.springboot_ecommerce.model.User;

import com.store.springboot_ecommerce.repository.CartItemRepo;
import com.store.springboot_ecommerce.repository.OrderRepo;


import jakarta.transaction.Transactional;


@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo ;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private CartService cartService;


    @Transactional
    public OrderResponseDto placeOrder(User user){
        List<CartItem> cartItems = cartService.getCartItems(user);

        if(cartItems.isEmpty()) throw new RuntimeException("cart is empty") ;

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("pending"); 

        List<OrderItems> orderItems =new ArrayList<>();

        double total = 0;

        for(CartItem cartItem : cartItems){

            Product product = cartItem.getProduct();
            if(product.getStock() < cartItem.getQuantity()){
                throw new RuntimeException("product " + product.getName()  +" unavailable" );
            }
          
            product.setStock(product.getStock() - cartItem.getQuantity());
            
            OrderItems item = new OrderItems();
            item.setOrder(order);
            item.setQuantity(cartItem.getQuantity());
            item.setPriceAtPurchase(cartItem.getProduct().getPrice().doubleValue());
            item.setProduct(cartItem.getProduct());

            total = item.getPriceAtPurchase() * item.getQuantity();
            orderItems.add(item);
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(total);

        Order saveOrder = orderRepo.save(order);
        cartItemRepo.deleteAll(cartItems);

        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setOrderId(order.getId());
        responseDto.setTotalPrice(order.getTotalPrice());
        responseDto.setStatus(order.getStatus());
        responseDto.setUserName(order.getUser().getUserName());


        List<OrderItemDto> itemDtos = new ArrayList<>();
        for(OrderItems oi : saveOrder.getOrderItems()){
            OrderItemDto oiDto = new OrderItemDto();
            oiDto.setId(oi.getId());
            oiDto.setPrice(oi.getPriceAtPurchase());
            oiDto.setProductName(oi.getProduct().getName());
            oiDto.setQuantity(oi.getQuantity());
            itemDtos.add(oiDto);
        }

        responseDto.setOrderItemDto(itemDtos);
        return responseDto;
    }


}