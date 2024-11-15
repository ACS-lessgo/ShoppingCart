package com.acs.Shopping.Cart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.acs.Shopping.Cart.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryAndBrand(String category, String brand);

    List<Product> findByProductName(String productName);

    List<Product> findByBrandAndName(String brand, String productName);

    Long countByBrandAndName(String brand, String name);
}
