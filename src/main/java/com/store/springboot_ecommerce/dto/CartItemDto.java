package com.store.springboot_ecommerce.dto;



public class CartItemDto {
        private String name;
        private double price ;
        private int quantity;
        private long productId;
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
         public Long getProductId() {
            return productId;
        }
        public void setProductId(Long productId) {
            this.productId = productId;
        }
        public double getPrice() {
            return price;
        }
        public void setPrice(double price) {
            this.price = price;
        }
        public int getQuantity() {
            return quantity;
        }
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
}