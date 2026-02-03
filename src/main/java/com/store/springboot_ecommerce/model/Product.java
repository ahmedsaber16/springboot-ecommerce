package com.store.springboot_ecommerce.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "products")
public class Product {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    
    @Column(nullable = false)
    private String name ;
    private String description ;
    @Column( nullable = false)
    private BigDecimal price ;
    @Min(value = 0)
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;





    public Product(String name, String description, BigDecimal price, @Min(0) int stock, Category category  ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stock = stock ;
    }

    public Product() {
        }


     public Long getId() {
        return id;
    }    

    public String getName() {
        return name;
    }
   public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

   public void setDescription(String description) {
        this.description = description;
    }
   public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

     public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }


    


}