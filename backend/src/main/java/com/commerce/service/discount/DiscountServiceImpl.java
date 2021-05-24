package com.commerce.service.discount;

import com.commerce.dao.DiscountRepository;
import com.commerce.error.exception.InvalidArgumentException;
import com.commerce.error.exception.ResourceNotFoundException;
import com.commerce.model.entity.Cart;
import com.commerce.model.entity.Discount;
import com.commerce.service.cart.CartService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final CartService cartService;

    @Override
    public void applyDiscount(String code) {

        Discount discount = discountRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Discount code not found"));

        if (!discount.getIsActive()) {
            throw new InvalidArgumentException("Discount code is expired!");
        }

        Cart cart = cartService.getCart();

        cart.setDiscount(discount);
        cartService.saveCart(cart);

    }

}
