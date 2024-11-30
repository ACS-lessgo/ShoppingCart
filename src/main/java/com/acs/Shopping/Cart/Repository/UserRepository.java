package com.acs.Shopping.Cart.Repository;

import com.acs.Shopping.Cart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
}
