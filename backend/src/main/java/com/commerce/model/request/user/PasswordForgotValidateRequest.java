package com.commerce.model.request.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.commerce.validation.PasswordConfirmation;
import com.commerce.validation.PasswordConfirmer;

import lombok.Data;

@Data
@PasswordConfirmation
public class PasswordForgotValidateRequest implements PasswordConfirmer {

    @NotBlank
    private String token;

    @NotBlank
    @Size(min = 6, max = 52)
    private String password;

    private String passwordConfirmation;
    
}
