package com.commerce.model.response.user;

import com.commerce.model.dto.AddressDTO;

import lombok.Data;

@Data
public class UserResponse {

    private String email;
    private String firstName;
    private String lastName;
    private AddressDTO address;    
    private Boolean isVerified;
    
}
