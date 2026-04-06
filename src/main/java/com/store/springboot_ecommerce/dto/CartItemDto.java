package com.store.springboot_ecommerce.dto;

import java.math.BigDecimal;

import com.store.springboot_ecommerce.model.CartItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
        private Long cartItemId;
        private Long productId;
        private String productName;

        private BigDecimal productPrice ;
        private BigDecimal totalPrice;
        private int quantity;

        public CartItemDto(CartItem item) {
                this.cartItemId = item.getId();
                this.productId = item.getProduct().getId();
                this.productName = item.getProduct().getName();
                this.productPrice = item.getProduct().getPrice();
                this.quantity = item.getQuantity();
                this.totalPrice = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

        }




}