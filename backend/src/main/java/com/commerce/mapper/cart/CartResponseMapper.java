package com.commerce.mapper.cart;

import java.util.function.Function;
import java.util.stream.Collectors;

import com.commerce.model.dto.DiscountDTO;
import com.commerce.model.entity.Cart;
import com.commerce.model.response.cart.CartResponse;
import com.commerce.service.cart.CartService;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class CartResponseMapper implements Function<Cart, CartResponse> {

    private CartItemDTOMapper cartItemDTOMapper;
    private CartService cartService;

    public CartResponseMapper(CartItemDTOMapper cartItemDTOMapper, @Lazy CartService cartService) {
        this.cartItemDTOMapper = cartItemDTOMapper;
        this.cartService = cartService;
    }

    @Override
    public CartResponse apply(Cart cart) {

        if (cart == null) {
            return null;
        }

        CartResponse cartResponse = new CartResponse();

        if (cart.getCartItems() != null) {
            cartResponse.setCartItems(cart.getCartItems().stream().map(cartItem -> cartItemDTOMapper.apply(cartItem))
                    .collect(Collectors.toList()));
        }

        if (cart.getDiscount() != null) {
            cartResponse.setDiscount(DiscountDTO.builder().discountPercent(cart.getDiscount().getDiscountPercent())
                    .isActive(cart.getDiscount().getIsActive()).build());
        }

        cartResponse.setTotalCartPrice(cartService.calculateTotalCartPrice(cart));
        cartResponse.setTotalShippingPrice(cartService.calculateTotalShippingPrice(cart));
        cartResponse.setTotalPrice(cartService.calculateTotalPrice(cart));

        return cartResponse;

    }

}
