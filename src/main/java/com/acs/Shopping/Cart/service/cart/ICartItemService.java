package com.acs.Shopping.Cart.service.cart;

import com.acs.Shopping.Cart.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId , Long productId , int quantity);
    void removeItemFromCart(Long cartId , Long productId);
    void updateItemQuantity(Long cartId , Long productId , int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
