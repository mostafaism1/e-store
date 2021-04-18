package com.commerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AddressDTO {

    private String country;
    private String state;
    private String city;
    private String address;
    private String zip;
    private String phone;

}
