package com.store.springboot_ecommerce.dto;

import java.util.List;

public class CartResponseDto {
    private String userName;
    private String email;
    private List<CartItemDto> items;
    private double totalCartPrice;


    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<CartItemDto> getItems() {
        return items;
    }
    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }
    public double getTotalCartPrice() {
        return totalCartPrice;
    }
    public void setTotalCartPrice(double totalCartPrice) {
        this.totalCartPrice = totalCartPrice;
    } 
}