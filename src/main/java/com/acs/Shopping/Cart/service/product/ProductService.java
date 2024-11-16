package com.acs.Shopping.Cart.service.product;

import com.acs.Shopping.Cart.Repository.CategoryRepository;
import com.acs.Shopping.Cart.exceptions.ResourceNotFoundException;
import com.acs.Shopping.Cart.model.Category;
import com.acs.Shopping.Cart.model.Product;
import com.acs.Shopping.Cart.Repository.ProductRepository;
import java.util.List;
import java.util.Optional;

import com.acs.Shopping.Cart.request.AddProductRequest;
import com.acs.Shopping.Cart.request.ProductUpdateRequest;
import com.acs.Shopping.Cart.util.ShoppingCartConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        // check if category exists
        // Is found , set it as product category
        // If no save it as a new category then set it to product category

        Category category = Optional.ofNullable(categoryRepository.findByName(
                request.getCategory().getName())).orElseGet(() ->{
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request , Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                request.getInventory(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(ShoppingCartConstants.PRODUCT_NOT_FOUND));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository :: delete ,() -> {throw new ResourceNotFoundException(ShoppingCartConstants.PRODUCT_NOT_FOUND);});
    }

    @Override
    public Product updateProductById(ProductUpdateRequest request, Long productId) {

        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct,request))
                .map(productRepository :: save)
                .orElseThrow(()->new ResourceNotFoundException(ShoppingCartConstants.PRODUCT_NOT_FOUND));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByName(String productName) {
        return productRepository.findByName(productName);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String productName) {
        return productRepository.findByBrandAndName(brand,productName);
    }

    @Override
    public Long countProductsByBrandAndName(String brand,String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}
