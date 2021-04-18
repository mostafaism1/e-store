package com.commerce.model.request.order;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.commerce.model.dto.AddressDTO;

import lombok.Data;

@Data
public class PostOrderRequest {

    @NotNull
    private AddressDTO shippingAddress;

    @NotNull
    private AddressDTO billingAddress;

    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String shippingCompany;

}
