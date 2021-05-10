package com.commerce.service.cart;

import com.commerce.model.entity.Cart;
import com.commerce.model.request.cart.ConfirmCartRequest;
import com.commerce.model.response.cart.CartResponse;

public class CartServiceImpl implements CartService {

    @Override
    public CartResponse addToCart(Long id, Integer amount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CartResponse incrementCartItem(Long cartItemId, Integer amount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CartResponse decrementCartItem(Long cartItemId, Integer amount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CartResponse fetchCart() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CartResponse removeFromCart(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean confirmCart(ConfirmCartRequest confirmCartRequest) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Cart getCart() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveCart(Cart cart) {
        // TODO Auto-generated method stub

    }

    @Override
    public void emptyCart() {
        // TODO Auto-generated method stub

    }

    @Override
    public Cart calculatePrice(Cart cart) {
        // TODO Auto-generated method stub
        return null;
    }

}
