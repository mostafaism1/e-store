package com.commerce.model.request.cart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RemoveFromCartRequest {

    @NotNull
    @Min(1)
    private Long cartItemId;

}
