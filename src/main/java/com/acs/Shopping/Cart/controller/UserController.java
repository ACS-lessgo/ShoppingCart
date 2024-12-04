package com.acs.Shopping.Cart.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acs.Shopping.Cart.dto.UserDto;
import com.acs.Shopping.Cart.exceptions.ResourceExistsException;
import com.acs.Shopping.Cart.model.User;
import com.acs.Shopping.Cart.request.CreateUserRequest;
import com.acs.Shopping.Cart.request.UpdateUserRequest;
import com.acs.Shopping.Cart.response.ApiResponse;
import com.acs.Shopping.Cart.service.user.IUserService;
import com.acs.Shopping.Cart.util.ShoppingCartConstants;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/images/image")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUsertoDto(user);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK,userDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_NOT_FOUND,e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUsertoDto(user);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK, userDto));
        } catch (ResourceExistsException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ShoppingCartConstants.HTTP_NOT_FOUND,e.getMessage()));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request , @PathVariable Long userId){
        try {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertUsertoDto(user);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK, userDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(ShoppingCartConstants.HTTP_BAD_REQUEST, e.getMessage()));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse(ShoppingCartConstants.HTTP_OK, ShoppingCartConstants.DELETE_SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ShoppingCartConstants.HTTP_NOT_FOUND, e.getMessage()));
        }
    }
}
