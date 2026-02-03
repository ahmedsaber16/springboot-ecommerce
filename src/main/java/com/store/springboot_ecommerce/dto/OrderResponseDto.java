package com.store.springboot_ecommerce.dto;

import java.util.List;

public class OrderResponseDto {
    private long orderId ;
    private String status;
    private double totalPrice;
    private String userName;
    private List<OrderItemDto> OrderItemDto;

    
    public OrderResponseDto() {
    }
    public OrderResponseDto(long orderId, String status, double totalPrice, String userName,
            List<com.store.springboot_ecommerce.dto.OrderItemDto> orderItemDto) {
        this.orderId = orderId;
        this.status = status;
        this.totalPrice = totalPrice;
        this.userName = userName;
        OrderItemDto = orderItemDto;
    }
    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public List<OrderItemDto> getOrderItemDto() {
        return OrderItemDto;
    }
    public void setOrderItemDto(List<OrderItemDto> orderItemDto) {
        OrderItemDto = orderItemDto;
    }


}