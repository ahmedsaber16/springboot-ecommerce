package com.store.springboot_ecommerce.dto;

import java.math.BigDecimal;

import com.store.springboot_ecommerce.model.OrderItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private long id;
    private String productName;
    private int quantity ;
    private BigDecimal price;

    public OrderItemDto(OrderItems item) {
        this.id = item.getId();
        this.productName = item.getProduct().getName();
        this.quantity = item.getQuantity();
        this.price = item.getPriceAtPurchase();
    }

}