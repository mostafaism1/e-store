package com.commerce.service.token;

import com.commerce.model.entity.User;
import com.commerce.model.request.user.PasswordForgotValidateRequest;

public interface TokenService {

    void createEmailConfirmToken(User user);    

    void validateEmail(String token);

    void createPasswordResetToken(String email);

    void validateForgotPasswordConfirm(String token);

    void validateForgotPassword(PasswordForgotValidateRequest passwordForgotValidateRequest);
}
