package com.commerce.service;

import com.commerce.model.entity.User;
import com.commerce.model.request.user.PasswordResetRequest;
import com.commerce.model.request.user.RegisterRequest;
import com.commerce.model.request.user.UpdateUserAddressRequest;
import com.commerce.model.request.user.UpdateUserRequest;
import com.commerce.model.response.user.UserResponse;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserResponse getCurrentUser() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserResponse saveUser(User user) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserResponse findByEmail(String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserResponse register(RegisterRequest registerRequest) {
        // TODO Auto-generated method stub
        return null;
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
