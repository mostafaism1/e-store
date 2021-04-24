package com.commerce.service;

import com.commerce.dao.UserRepository;
import com.commerce.error.exception.AccessDeniedException;
import com.commerce.error.exception.InvalidArgumentException;
import com.commerce.error.exception.ResourceNotFoundException;
import com.commerce.mapper.user.UserResponseMapper;
import com.commerce.model.common.Address;
import com.commerce.model.entity.User;
import com.commerce.model.request.user.PasswordResetRequest;
import com.commerce.model.request.user.RegisterRequest;
import com.commerce.model.request.user.UpdateUserAddressRequest;
import com.commerce.model.request.user.UpdateUserRequest;
import com.commerce.model.response.user.UserResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserResponseMapper userResponseMapper;

    @Override
    public UserResponse getCurrentUser() {

        User user = getAuthenticatedUser();

        return userResponseMapper.apply(user);

    }

    @Override
    public UserResponse saveUser(User user) {

        if (user == null) {
            throw new InvalidArgumentException("Null user");
        }

        User savedUser = userRepository.save(user);
        return userResponseMapper.apply(savedUser);

    }

    @Override
    public UserResponse findByEmail(String email) {

        if (email == null) {
            throw new InvalidArgumentException("Null email");
        }

        User user = userRepository.findByEmail(email).orElse(null);
        return userResponseMapper.apply(user);

    }

    @Override
    public UserResponse register(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new InvalidArgumentException("An account already exists with this email");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setIsVerified(false);

        user = userRepository.save(user);

        return userResponseMapper.apply(user);

    }

    @Override
    public UserResponse updateUser(UpdateUserRequest updateUserRequest) {

        User user = getAuthenticatedUser();
        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.getAddress().setPhone(updateUserRequest.getPhone());

        User savedUser = userRepository.save(user);

        return userResponseMapper.apply(savedUser);

    }

    @Override
    public UserResponse updateUserAddress(UpdateUserAddressRequest updateUserAddressRequest) {

        User user = getAuthenticatedUser();

        Address updatedAddress = new Address();
        updatedAddress.setCountry(updateUserAddressRequest.getCountry());
        updatedAddress.setState(updateUserAddressRequest.getState());
        updatedAddress.setCity(updateUserAddressRequest.getCity());
        updatedAddress.setAddress(updateUserAddressRequest.getAddress());
        updatedAddress.setZip(updateUserAddressRequest.getZip());
        user.setAddress(updatedAddress);

        User savedUser = userRepository.save(user);

        return userResponseMapper.apply(savedUser);

    }

    @Override
    public void resetPassword(PasswordResetRequest passwordResetRequest) {

        User user = getAuthenticatedUser();

        if (passwordEncoder.matches(passwordResetRequest.getOldPassword(), user.getPassword())) {
            throw new InvalidArgumentException("Invalid password");
        }

        user.setPassword(passwordEncoder.encode(passwordResetRequest.getPassword()));

        userRepository.save(user);

    }

    @Override
    public Boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean getVerificationStatus() {
        
        User user = getAuthenticatedUser();
        return user.getIsVerified();

    }

    private User getAuthenticatedUser() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email == null) {
            throw new AccessDeniedException("Invalid access");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user;

    }

}
