package com.commerce.api;

import javax.validation.Valid;

import com.commerce.model.request.cart.AddToCartRequest;
import com.commerce.model.request.cart.ConfirmCartRequest;
import com.commerce.model.request.cart.DecrementCartItemRequest;
import com.commerce.model.request.cart.IncrementCartItemRequest;
import com.commerce.model.request.cart.RemoveFromCartRequest;
import com.commerce.model.response.cart.CartResponse;
import com.commerce.service.cart.CartService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponse> addToCart(@RequestBody @Valid AddToCartRequest addToCartRequest) {

        CartResponse cartResponse = cartService.addToCart(addToCartRequest.getProductVariantId(),
                addToCartRequest.getQuantity());
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);

    }

    @PostMapping(path = "/increment")
    public ResponseEntity<CartResponse> increaseCartItem(
            @RequestBody @Valid IncrementCartItemRequest incrementCartItemRequest) {

        CartResponse cartResponse = cartService.incrementCartItem(incrementCartItemRequest.getCartItemId(),
                incrementCartItemRequest.getQuantity());
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);

    }

    @PostMapping(path = "/decrement")
    public ResponseEntity<CartResponse> decreaseCartItem(
            @RequestBody @Valid DecrementCartItemRequest decrementCartItemRequest) {

        CartResponse cartResponse = cartService.decrementCartItem(decrementCartItemRequest.getCartItemId(),
                decrementCartItemRequest.getQuantity());
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<CartResponse> fetchCart() {

        CartResponse cartResponse = cartService.fetchCart();
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);

    }

    @PostMapping(path = "/remove")
    public ResponseEntity<CartResponse> removeFromCart(
            @RequestBody @Valid RemoveFromCartRequest removeFromCartRequest) {

        CartResponse cartResponse = cartService.removeFromCart(removeFromCartRequest.getCartItemId());
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> emptyCart() {

        cartService.emptyCart();
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping(path = "/confirm")
    public ResponseEntity<Boolean> confirmCart(@RequestBody @Valid ConfirmCartRequest confirmCartRequest) {

        Boolean confirmCart = cartService.confirmCart(confirmCartRequest);
        return new ResponseEntity<>(confirmCart, HttpStatus.OK);

    }

}
