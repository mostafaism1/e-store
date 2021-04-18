package com.commerce.model.request.user;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ValidateEmailRequest {

    @NotBlank
    private String token;

}
