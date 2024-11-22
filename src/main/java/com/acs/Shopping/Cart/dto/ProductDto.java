package com.acs.Shopping.Cart.dto;

import com.acs.Shopping.Cart.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDto {
    private long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private LocalDateTime createdAt;
    private Category category;
    private List<ImageDto> images;
}
