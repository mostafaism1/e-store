package com.commerce.model.request.cart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AddToCartRequest {

    @NotNull
    @Min(1)
    private Long productVariantId;

    @NotNull
    @Min(1)
    private Integer quantity;

}
