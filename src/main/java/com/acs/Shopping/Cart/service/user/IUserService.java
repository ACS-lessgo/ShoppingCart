package com.acs.Shopping.Cart.service.user;

import com.acs.Shopping.Cart.model.User;
import com.acs.Shopping.Cart.request.CreateUserRequest;
import com.acs.Shopping.Cart.request.UpdateUserRequest;

public interface IUserService {
    User getUserById (Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request , Long userId);
    void deleteUser(Long userId);
}
