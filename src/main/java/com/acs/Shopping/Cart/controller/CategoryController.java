package com.acs.Shopping.Cart.controller;

import com.acs.Shopping.Cart.exceptions.ResourceExistsException;
import com.acs.Shopping.Cart.exceptions.ResourceNotFoundException;
import com.acs.Shopping.Cart.model.Category;
import com.acs.Shopping.Cart.response.ApiResponse;
import com.acs.Shopping.Cart.service.category.CategoryService;
import com.acs.Shopping.Cart.util.ShoppingCartConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try{
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK,categories,ShoppingCartConstants.RESOURCE_FOUND));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ShoppingCartConstants.HTTP_INTERNAL_SERVER_ERROR,null, e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody  Category category){
        try {
            Category savedCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK,savedCategory,ShoppingCartConstants.RESOURCE_FOUND));
        }catch (ResourceExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/{id}/getById")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
        try{
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK,category,ShoppingCartConstants.RESOURCE_FOUND));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ShoppingCartConstants.HTTP_INTERNAL_SERVER_ERROR,null, e.getMessage()));
        }
    }

    @GetMapping("/{name}/getByName")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try{
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK,category,ShoppingCartConstants.RESOURCE_FOUND));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ShoppingCartConstants.HTTP_INTERNAL_SERVER_ERROR,null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        try{
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK,null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ShoppingCartConstants.HTTP_INTERNAL_SERVER_ERROR,null, e.getMessage()));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category category, @PathVariable Long id){
        try{
            Category updatedCategory = categoryService.updateCategory(category, id);
            System.out.println(updatedCategory);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK, updatedCategory,ShoppingCartConstants.UPDATE_SUCCESS));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ShoppingCartConstants.HTTP_INTERNAL_SERVER_ERROR,null, e.getMessage()));
        }
    }
}
