package com.commerce.service;

import com.commerce.dao.UserRepository;
import com.commerce.error.exception.AccessDeniedException;
import com.commerce.error.exception.InvalidArgumentException;
import com.commerce.error.exception.ResourceNotFoundException;
import com.commerce.mapper.user.UserResponseMapper;
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

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email == null) {
            throw new AccessDeniedException("Invalid access");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserResponse updateUserAddress(UpdateUserAddressRequest updateUserAddressRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean userExists(String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean getVerificationStatus() {
        // TODO Auto-generated method stub
        return null;
    }

}
