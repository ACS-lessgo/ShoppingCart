package com.acs.Shopping.Cart.service.cart;

import com.acs.Shopping.Cart.Repository.CartItemRepository;
import com.acs.Shopping.Cart.Repository.CartRepository;
import com.acs.Shopping.Cart.exceptions.ResourceNotFoundException;
import com.acs.Shopping.Cart.model.Cart;
import com.acs.Shopping.Cart.model.CartItem;
import com.acs.Shopping.Cart.model.Product;
import com.acs.Shopping.Cart.service.product.IProductService;
import com.acs.Shopping.Cart.util.ShoppingCartConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{

    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //1. get the cart
        //2. get the product
        //3. check if the product is already in the cart
        //4. if yes , update the quantity else initialize a new cart item
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());

        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }else{
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem removeItem = getCartItem(cartId, productId);
        if(removeItem.getId() != null) cart.removeItem(removeItem);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId){
        Cart cart = cartService.getCart(cartId);
        return   cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException(ShoppingCartConstants.PRODUCT_NOT_FOUND));
    }
}
