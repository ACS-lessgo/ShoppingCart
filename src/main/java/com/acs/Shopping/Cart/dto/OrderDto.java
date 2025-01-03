package com.acs.Shopping.Cart.dto;

import com.acs.Shopping.Cart.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long orderId;
    private LocalDate orderDate;
    private LocalDateTime orderCreatedAt;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItemDtoList;
}
