package com.acs.Shopping.Cart.service.order;

import com.acs.Shopping.Cart.Repository.OrderRepository;
import com.acs.Shopping.Cart.Repository.ProductRepository;
import com.acs.Shopping.Cart.dto.OrderDto;
import com.acs.Shopping.Cart.enums.OrderStatus;
import com.acs.Shopping.Cart.exceptions.ResourceNotFoundException;
import com.acs.Shopping.Cart.model.Cart;
import com.acs.Shopping.Cart.model.Order;
import com.acs.Shopping.Cart.model.OrderItem;
import com.acs.Shopping.Cart.model.Product;
import com.acs.Shopping.Cart.service.cart.CartService;
import com.acs.Shopping.Cart.util.ShoppingCartConstants;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCart(userId);

        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order,cart);

        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        order.setOrderCreatedAt(LocalDateTime.now());
        
        return order;
    }

    private List<OrderItem> createOrderItems(Order order , Cart cart) {
        return cart.getCartItems().stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    product.setInventory(product.getInventory() - cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(order, product, cartItem.getQuantity(), product.getPrice());
                }).toList();
    }


    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList.stream()
                .map(item -> item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToOrderDto)
                .orElseThrow(() -> new ResourceNotFoundException(ShoppingCartConstants.ORDER_NOT_FOUND));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId){
        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::convertToOrderDto)
                .toList();
    }

    private OrderDto convertToOrderDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
