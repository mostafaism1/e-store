package com.commerce.model.request.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UpdateUserAddressRequest {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String country;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String state;

    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    @NotBlank
    private String city;

    @NotBlank
    @Pattern(regexp = "[0-9a-zA-Z #,-]+")
    private String address;

    @NotBlank
    @Pattern(regexp = "^[0-9]*$")
    private String zip;

}
