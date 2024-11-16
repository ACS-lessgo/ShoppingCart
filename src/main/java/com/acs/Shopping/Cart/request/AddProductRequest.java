package com.acs.Shopping.Cart.request;

import com.acs.Shopping.Cart.model.Category;
import com.acs.Shopping.Cart.model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AddProductRequest {
    private long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<Image> images;
    private LocalDateTime createdAt;
}
