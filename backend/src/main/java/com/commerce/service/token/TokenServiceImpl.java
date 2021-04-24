package com.commerce.service.token;

import com.commerce.model.entity.User;
import com.commerce.model.request.user.PasswordForgotValidateRequest;

public class TokenServiceImpl implements TokenService {

    @Override
    public void createEmailConfirmToken(User user) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void validateEmail(String token) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void createPasswordResetToken(String email) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void validateForgotPasswordConfirm(String token) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void validateForgotPassword(PasswordForgotValidateRequest passwordForgotValidateRequest) {
        // TODO Auto-generated method stub
        
    }
    
}
