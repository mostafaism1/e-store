package com.commerce.model.response.cart;

import java.util.List;

import com.commerce.model.dto.CartItemDTO;
import com.commerce.model.dto.DiscountDTO;

import lombok.Data;

@Data
public class CartResponse {

    private List<CartItemDTO> cartItems;
    private DiscountDTO discount;
    private Double totalCartPrice;
    private Double totalShippingPrice;
    private Double totalPrice;

}
