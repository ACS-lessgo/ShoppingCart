package com.acs.Shopping.Cart.Repository;

import com.acs.Shopping.Cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long id);
}
