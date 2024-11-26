package com.acs.Shopping.Cart.service.cart;

import com.acs.Shopping.Cart.Repository.CartItemRepository;
import com.acs.Shopping.Cart.Repository.CartRepository;
import com.acs.Shopping.Cart.exceptions.ResourceNotFoundException;
import com.acs.Shopping.Cart.model.Cart;
import com.acs.Shopping.Cart.util.ShoppingCartConstants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // temp to generate cart id
    private final AtomicLong cartIdGenerator = new AtomicLong();

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException(ShoppingCartConstants.CART_NOT_FOUND));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Long initCart() {
        Cart cart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        cart.setId(newCartId);
        return cartRepository.save(cart).getId();
    }
}
