package com.commerce.mapper.cart;

import java.util.function.Function;

import com.commerce.mapper.color.ColorDTOMapper;
import com.commerce.model.dto.CartItemDTO;
import com.commerce.model.entity.CartItem;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CartItemDTOMapper implements Function<CartItem, CartItemDTO> {

    private final ColorDTOMapper colorDTOMapper;

    @Override
    public CartItemDTO apply(CartItem cartItem) {

        if (cartItem == null) {
            return null;
        }

        return CartItemDTO.builder().id(cartItem.getId()).url(cartItem.getProductVariant().getProduct().getUrl())
                .name(cartItem.getProductVariant().getProduct().getName())
                .price(cartItem.getProductVariant().getPrice()).quantity(cartItem.getQuantity())
                .thumbURL(cartItem.getProductVariant().getThumbURL()).stock(cartItem.getProductVariant().getStock())
                .color(colorDTOMapper.apply(cartItem.getProductVariant().getColor())).build();

    }

}
