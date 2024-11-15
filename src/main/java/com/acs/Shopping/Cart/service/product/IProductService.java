package com.acs.Shopping.Cart.service.product;

import com.acs.Shopping.Cart.model.Product;
import com.acs.Shopping.Cart.request.AddProductRequest;
import com.acs.Shopping.Cart.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);

    void deleteProductById(Long id);
    Product updateProductById(ProductUpdateRequest request, Long productId);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category,String brand);
    List<Product> getProductByName(String productName);
    List<Product> getProductByBrandAndName(String brand,String productName);

    Long countProductsByBrandAndName(String brand,String name);
}
