package com.acs.Shopping.Cart.service.user;

import com.acs.Shopping.Cart.Repository.UserRepository;
import com.acs.Shopping.Cart.exceptions.ResourceNotFoundException;
import com.acs.Shopping.Cart.model.User;
import com.acs.Shopping.Cart.request.CreateUserRequest;
import com.acs.Shopping.Cart.request.UpdateUserRequest;
import com.acs.Shopping.Cart.util.ShoppingCartConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).
                orElseThrow(() ->new ResourceNotFoundException(ShoppingCartConstants.USER_NOT_FOUND));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req ->{
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setPassword(request.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException(String.format(ShoppingCartConstants.USER_ALREADY_EXISTS,request.getEmail())));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser ->{
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException(ShoppingCartConstants.USER_NOT_FOUND));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).
                ifPresentOrElse(userRepository :: delete,
                        () -> {throw new ResourceNotFoundException(ShoppingCartConstants.USER_NOT_FOUND);
                });
    }
}
