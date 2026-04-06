package com.store.springboot_ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private long orderId ;
    private String status;
    private BigDecimal totalPrice;
    private String userName;
    private List<OrderItemDto> items;

}