package com.acs.Shopping.Cart.service.order;

import com.acs.Shopping.Cart.dto.OrderDto;
import com.acs.Shopping.Cart.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
