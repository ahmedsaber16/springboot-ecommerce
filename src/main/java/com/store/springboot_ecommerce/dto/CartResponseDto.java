package com.store.springboot_ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {
    private String userName;
    private String email;
    private List<CartItemDto> items;
    private BigDecimal totalCartPrice;


}