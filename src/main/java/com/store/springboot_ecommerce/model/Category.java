package com.store.springboot_ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long categoryId ;
    @Column(nullable = false , unique = true)
    private String name;
    private String description;         

    @JsonIgnore
    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Product> products = new ArrayList<>(); 

   

    public Category(String name, String description, List<Product> products) {
        this.name = name;
        this.description = description;
        this.products = products;
    }



    public Category() {
    }



  public Long getCategoryId() {
        return categoryId;
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

    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}