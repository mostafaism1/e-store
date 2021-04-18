package com.commerce.model.request.user;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UpdateUserRequest {

    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String lastName;

    @Pattern(regexp = "[0-9]+")
    @Size(min = 11, max = 12)
    private String phone;

}
