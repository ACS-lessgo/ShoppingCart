package com.acs.Shopping.Cart.service.order;

import com.acs.Shopping.Cart.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
}
