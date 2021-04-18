package com.commerce.model.request.user;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PasswordForgotConfirmRequest {

    @NotBlank
    private String token;

}
