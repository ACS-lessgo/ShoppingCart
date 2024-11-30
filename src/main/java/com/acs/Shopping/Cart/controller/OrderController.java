package com.acs.Shopping.Cart.controller;

import com.acs.Shopping.Cart.dto.OrderDto;
import com.acs.Shopping.Cart.model.Order;
import com.acs.Shopping.Cart.response.ApiResponse;
import com.acs.Shopping.Cart.service.order.IOrderService;
import com.acs.Shopping.Cart.util.ShoppingCartConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK, order, ShoppingCartConstants.ORDER_CREATED));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ShoppingCartConstants.HTTP_INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @GetMapping("{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(Long orderId){
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK, order, ShoppingCartConstants.RESOURCE_FOUND));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ShoppingCartConstants.HTTP_INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @GetMapping("{userId}/orders")
    public ResponseEntity<ApiResponse> getUserOrders(Long userId){
        try {
            List<OrderDto> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK, orders, ShoppingCartConstants.RESOURCE_FOUND));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ShoppingCartConstants.HTTP_INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }
}
