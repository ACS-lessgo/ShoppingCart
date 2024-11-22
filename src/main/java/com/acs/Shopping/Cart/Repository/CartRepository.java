package com.acs.Shopping.Cart.Repository;

import com.acs.Shopping.Cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
