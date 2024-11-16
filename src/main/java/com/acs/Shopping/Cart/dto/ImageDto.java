package com.acs.Shopping.Cart.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImageDto {
    private Long imageId;
    private String imageName;
    private String downloadUrl;
    private LocalDateTime createdAt;
}
