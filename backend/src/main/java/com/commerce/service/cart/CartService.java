package com.commerce.service.cart;

import com.commerce.model.entity.Cart;
import com.commerce.model.request.cart.ConfirmCartRequest;
import com.commerce.model.response.cart.CartResponse;

public interface CartService {

    CartResponse addToCart(Long id, Integer amount);

    CartResponse incrementCartItem(Long cartItemId, Integer amount);

    CartResponse decrementCartItem(Long cartItemId, Integer amount);

    CartResponse fetchCart();

    CartResponse removeFromCart(Long id);

    boolean confirmCart(ConfirmCartRequest confirmCartRequest);

    Cart getCart();

    void saveCart(Cart cart);

    void emptyCart();

    Cart calculatePrice(Cart cart);

}
