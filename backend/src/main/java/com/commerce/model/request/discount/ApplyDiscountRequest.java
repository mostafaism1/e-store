package com.commerce.model.request.discount;

import javax.validation.constraints.NotBlank;

public class ApplyDiscountRequest {

    @NotBlank
    private String code;

}
