package com.acs.Shopping.Cart.controller;

import com.acs.Shopping.Cart.dto.ProductDto;
import com.acs.Shopping.Cart.exceptions.ResourceNotFoundException;
import com.acs.Shopping.Cart.model.Product;
import com.acs.Shopping.Cart.request.AddProductRequest;
import com.acs.Shopping.Cart.request.ProductUpdateRequest;
import com.acs.Shopping.Cart.response.ApiResponse;
import com.acs.Shopping.Cart.service.product.ProductService;
import com.acs.Shopping.Cart.util.ShoppingCartConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        try{
            List<Product> products = productService.getAllProducts();
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK,convertedProducts,ShoppingCartConstants.RESOURCE_FOUND));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST,null, e.getMessage()));
        }
    }

    @GetMapping("/product/{productId}/getById")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable  Long productId){
        try{
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK,productDto,ShoppingCartConstants.RESOURCE_FOUND));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST,null, e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try{
            Product createProduct = productService.addProduct(product);
            ProductDto productDto = productService.convertToDto(createProduct);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK, productDto));
        }catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST,null, e.getMessage()));
        }
    }

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest product, @PathVariable Long productId){
        try{
            Product updateProduct = productService.updateProductById(product, productId);
            ProductDto productDto = productService.convertToDto(updateProduct);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK, productDto,ShoppingCartConstants.UPDATE_SUCCESS));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST,null, e.getMessage()));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try{
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK,null,ShoppingCartConstants.DELETE_SUCCESS));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST,null, e.getMessage()));
        }
    }

    @GetMapping("/by/brand-product")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName){
            try{
                List<Product> products = productService.getProductByBrandAndName(brandName, productName);
                if(products.isEmpty()){
                    return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_NOT_FOUND,null,ShoppingCartConstants.PRODUCT_NOT_FOUND));
                }
                List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
                return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK,convertedProducts,ShoppingCartConstants.RESOURCE_FOUND));
            }catch (Exception e){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST,null, e.getMessage()));
            }
    }

    @GetMapping("/by/category-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String brandName, @RequestParam String categoryName){
        try{
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brandName);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_NOT_FOUND,null,ShoppingCartConstants.PRODUCT_NOT_FOUND));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK,convertedProducts,ShoppingCartConstants.RESOURCE_FOUND));
        }catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST,null, e.getMessage()));
        }
    }

    @GetMapping("/by/product")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam String productName){
        try{
            List<Product> products = productService.getProductByName(productName);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_NOT_FOUND,null,ShoppingCartConstants.PRODUCT_NOT_FOUND));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK,convertedProducts,ShoppingCartConstants.RESOURCE_FOUND));
        }catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST,null, e.getMessage()));
        }
    }

    @GetMapping("/by/brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brandName){
        try{
            List<Product> products = productService.getProductsByBrand(brandName);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_NOT_FOUND,null,ShoppingCartConstants.PRODUCT_NOT_FOUND));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK,convertedProducts,ShoppingCartConstants.RESOURCE_FOUND));
        }catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST,null, e.getMessage()));
        }
    }

    @GetMapping("/by/{category}/all/")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category){
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_NOT_FOUND,null,ShoppingCartConstants.PRODUCT_NOT_FOUND));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK,convertedProducts,ShoppingCartConstants.RESOURCE_FOUND));
        }catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST,null, e.getMessage()));
        }
    }

    @GetMapping("/count-by/brand-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brandName , @RequestParam String productName){
        try {
            var count = productService.countProductsByBrandAndName(brandName, productName);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK,count,ShoppingCartConstants.RESOURCE_FOUND));
        }catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST,null, e.getMessage()));
        }
    }
}
