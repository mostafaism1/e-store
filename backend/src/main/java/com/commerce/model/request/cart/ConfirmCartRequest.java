package com.commerce.model.request.cart;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.commerce.model.dto.CartItemDTO;
import com.commerce.model.dto.DiscountDTO;

import lombok.Data;

@Data
public class ConfirmCartRequest {

    @NotNull
    private List<CartItemDTO> cartItems;

    private DiscountDTO discount;

    @Min(0)
    private Double totalCartPrice;

    @Min(0)
    private Double totalShippingPrice;

    @Min(0)
    private Double totalPrice;

}
