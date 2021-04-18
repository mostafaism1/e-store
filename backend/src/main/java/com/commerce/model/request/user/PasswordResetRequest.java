package com.commerce.model.request.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.commerce.validation.PasswordConfirmer;

import lombok.Data;

@Data
public class PasswordResetRequest implements PasswordConfirmer {

    @NotBlank
    @Size(min = 6, max = 52)
    private String oldPassword;

    @NotBlank
    @Size(min = 6, max = 52)
    private String password;

    @NotBlank
    @Size(min = 6, max = 52)
    private String passwordConfirmation;

}
