package com.acs.Shopping.Cart.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    // if a product is deleted , removes all the images for that
    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    public Product(String name, String brand, String description, BigDecimal price, int inventory, Category category,LocalDateTime createdAt) {
        this.brand = brand;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inventory = inventory;
        this.category = category;
        this.createdAt = createdAt;
    }

    public Product(String name, String brand, String description, BigDecimal price, int inventory, Category category) {
        this.brand = brand;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inventory = inventory;
        this.category = category;
        this.createdAt = LocalDateTime.now();
    }
}

