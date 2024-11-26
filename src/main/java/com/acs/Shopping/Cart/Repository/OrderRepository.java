package com.acs.Shopping.Cart.Repository;

import com.acs.Shopping.Cart.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
