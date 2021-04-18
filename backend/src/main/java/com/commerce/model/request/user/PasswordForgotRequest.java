package com.commerce.model.request.user;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class PasswordForgotRequest {

    @Email
    private String email;

}
