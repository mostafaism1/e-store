package com.commerce.model.request.discount;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ApplyDiscountRequest {

    @NotBlank
    private String code;

}
