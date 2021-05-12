package com.commerce.service.user;

import com.commerce.model.entity.User;
import com.commerce.model.request.user.PasswordResetRequest;
import com.commerce.model.request.user.RegisterRequest;
import com.commerce.model.request.user.UpdateUserAddressRequest;
import com.commerce.model.request.user.UpdateUserRequest;
import com.commerce.model.response.user.UserResponse;

public interface UserService {

    UserResponse getCurrentUser();

    UserResponse saveUser(User user);

    UserResponse findByEmail(String email);

    UserResponse register(RegisterRequest registerRequest);

    UserResponse updateUser(UpdateUserRequest updateUserRequest);

    UserResponse updateUserAddress(UpdateUserAddressRequest updateUserAddressRequest);

    void resetPassword(PasswordResetRequest passwordResetRequest);

    Boolean userExists(String email);

    Boolean getVerificationStatus();

}
