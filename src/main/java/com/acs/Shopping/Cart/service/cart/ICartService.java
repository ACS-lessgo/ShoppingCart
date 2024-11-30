package com.acs.Shopping.Cart.service.cart;

import com.acs.Shopping.Cart.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initCart();

    Cart getCartByUserId(Long userId);
}
