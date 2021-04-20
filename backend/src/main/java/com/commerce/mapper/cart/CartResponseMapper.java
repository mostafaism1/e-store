package com.commerce.mapper.cart;

import java.util.function.Function;
import java.util.stream.Collectors;

import com.commerce.model.entity.Cart;
import com.commerce.model.response.cart.CartResponse;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CartResponseMapper implements Function<Cart, CartResponse> {

    private CartItemDTOMapper cartItemDTOMapper;

    @Override
    public CartResponse apply(Cart cart) {

        if (cart == null) {
            return null;
        }

        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartItems(cart.getCartItems().stream().map(cartItem -> cartItemDTOMapper.apply(cartItem))
                .collect(Collectors.toList()));

        return cartResponse;

    }

}
