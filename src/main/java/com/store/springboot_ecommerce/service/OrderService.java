package com.store.springboot_ecommerce.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;

import com.store.springboot_ecommerce.model.Order;
import com.store.springboot_ecommerce.model.OrderItems;
import com.store.springboot_ecommerce.model.OrderStatus;
import com.store.springboot_ecommerce.model.Product;
import com.store.springboot_ecommerce.dto.OrderItemDto;
import com.store.springboot_ecommerce.dto.OrderResponseDto;
import com.store.springboot_ecommerce.model.CartItem;
import com.store.springboot_ecommerce.model.User;

import com.store.springboot_ecommerce.repository.CartItemRepo;
import com.store.springboot_ecommerce.repository.OrderRepo;
import com.store.springboot_ecommerce.repository.ProductRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo ;

    private final ProductRepo productRepo;

    private final CartItemRepo cartItemRepo;



    @Transactional
    public OrderResponseDto placeOrder(User user){
        List<CartItem> cartItems = cartItemRepo.findByUser(user);

        if(cartItems.isEmpty()) throw new RuntimeException("cart is empty") ;

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);;

        List<OrderItems> orderItems =new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;

        for(CartItem cartItem : cartItems){

            Product product = cartItem.getProduct();
            if(product.getStock() < cartItem.getQuantity()){
                throw new RuntimeException("product " + product.getName()  +" unavailable" );
            }

            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepo.save(product);

            OrderItems item = new OrderItems();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(cartItem.getQuantity());
            item.setPriceAtPurchase(product.getPrice());


            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            orderItems.add(item);
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(total);

        Order saveOrder = orderRepo.save(order);
        cartItemRepo.deleteAll(cartItems);

        return convertToResponseDto(saveOrder);

    }

    private OrderResponseDto convertToResponseDto(Order order){
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setOrderId(order.getId());
        responseDto.setTotalPrice(order.getTotalPrice());
        responseDto.setStatus(order.getStatus().name());
        responseDto.setUserName(order.getUser().getUserName());

        List<OrderItemDto> itemDtos = order.getOrderItems().stream()
        .map(OrderItemDto::new)
        .toList();
        responseDto.setItems(itemDtos);
        return responseDto;
    }


}