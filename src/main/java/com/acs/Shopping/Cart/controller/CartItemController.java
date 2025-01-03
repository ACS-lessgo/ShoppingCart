package com.acs.Shopping.Cart.controller;

import com.acs.Shopping.Cart.response.ApiResponse;
import com.acs.Shopping.Cart.service.cart.ICartItemService;
import com.acs.Shopping.Cart.service.cart.ICartService;
import com.acs.Shopping.Cart.util.ShoppingCartConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;

    @PostMapping("/addItemToCart")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId
            ,@RequestParam Long itemId ,@RequestParam Integer quantity){
        try{
            if(cartId == null) cartId = cartService.initCart();
            cartItemService.addItemToCart(cartId , itemId , quantity);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK, null,ShoppingCartConstants.ITEM_ADDED_TO_CART));
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_NOT_FOUND, null, e.getMessage()));
        }
    }

    @DeleteMapping("/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartId
            ,@PathVariable Long itemId){
        try{
            cartItemService.removeItemFromCart(cartId , itemId);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK, null,ShoppingCartConstants.ITEM_DELETED_FROM_CART));
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_NOT_FOUND, null, e.getMessage()));
        }
    }

    @PutMapping("/{cartId}/item/{itemId}/update-quantity")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
            @PathVariable Long itemId,@RequestParam Integer quantity){
        try{
            cartItemService.updateItemQuantity(cartId , itemId , quantity);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK, null,ShoppingCartConstants.UPDATE_SUCCESS));
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_NOT_FOUND, null, e.getMessage()));
        }
    }
}
