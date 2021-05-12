package com.commerce.service.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.commerce.dao.CartRepository;
import com.commerce.dao.ProductVariantRepository;
import com.commerce.dao.UserRepository;
import com.commerce.error.exception.InvalidArgumentException;
import com.commerce.error.exception.ResourceNotFoundException;
import com.commerce.mapper.cart.CartResponseMapper;
import com.commerce.model.entity.Cart;
import com.commerce.model.entity.CartItem;
import com.commerce.model.entity.ProductVariant;
import com.commerce.model.entity.User;
import com.commerce.model.request.cart.ConfirmCartRequest;
import com.commerce.model.response.cart.CartResponse;
import com.commerce.model.response.user.UserResponse;
import com.commerce.service.user.UserService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CartRepository cartRepository;
    private final CartResponseMapper cartResponseMapper;
    private Optional<CartItem> cartItem;

    @Override
    public CartResponse addToCart(Long id, Integer amount) {

        User user = getCurrentUser();
        Cart cart = getUserCart(user);

        CartItem cartItem;
        ProductVariant productVariant;
        if (containsProductVariant(cart, id)) {
            cartItem = getCartProductVariant(id, cart);
            productVariant = cartItem.getProductVariant();

        } else {
            cartItem = new CartItem();
            productVariant = productVariantRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Could not find any product variant with the id " + id));
            cartItem.setCart(cart);
            cartItem.setProductVariant(productVariant);
            cartItem.setQuantity(0);

            List<CartItem> cartItems = cart.getCartItems();
            cartItems.add(cartItem);
            cart.setCartItems(cartItems);
        }

        if (productVariantHasEnoughStock(productVariant, cartItem.getQuantity() + amount)) {
            cartItem.setQuantity(cartItem.getQuantity() + amount);
        } else {
            throw new InvalidArgumentException("Product does not have desired stock.");
        }
        
        cart = cartRepository.save(cart);

        return cartResponseMapper.apply(cart);

    }

    @Override
    public CartResponse incrementCartItem(Long cartItemId, Integer amount) {

        User user = getCurrentUser();
        Cart cart = user.getCart();
        if (cart == null) {
            throw new ResourceNotFoundException("Empty cart");
        }

        CartItem cartItem = getCartItem(cart, cartItemId);
        incrementCartItem(cartItem, amount);

        cartRepository.save(cart);

        return cartResponseMapper.apply(cart);

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

    private boolean productVariantHasEnoughStock(ProductVariant productVariant, Integer amount) {
        return productVariant.getStock() >= amount;
    }

    private boolean containsProductVariant(Cart cart, Long productVariantId) {

        Optional<CartItem> cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProductVariant().getId() == productVariantId).findAny();

        return cartItem.isPresent() ? true : false;

    }

    private CartItem getCartProductVariant(Long productVariantId, Cart cart) {
        return cart.getCartItems().stream().filter(item -> item.getProductVariant().getId() == productVariantId)
                .findAny().orElseGet(CartItem::new);
    }

    private Cart getUserCart(User user) {
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }

        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());
        }
        return cart;
    }

    private User getCurrentUser() {
        UserResponse currentUser = userService.getCurrentUser();
        User user = userRepository.findByEmail(currentUser.getEmail()).get();
        return user;
    }

    private void incrementCartItem(CartItem cartItem, Integer amount) {
        if (productVariantHasEnoughStock(cartItem.getProductVariant(), cartItem.getQuantity() + amount)) {
            cartItem.setQuantity(cartItem.getQuantity() + amount);
        } else {
            throw new InvalidArgumentException("Product does not have desired stock.");
        }
    }

    private CartItem getCartItem(Cart cart, Long cartItemId) {
        CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getId() == cartItemId).findAny()
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found"));
        return cartItem;
    }
}
