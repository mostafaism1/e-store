package com.commerce.service.cart;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.commerce.dao.CartRepository;
import com.commerce.dao.ProductRepository;
import com.commerce.dao.ProductVariantRepository;
import com.commerce.dao.UserRepository;
import com.commerce.error.exception.InvalidArgumentException;
import com.commerce.error.exception.ResourceNotFoundException;
import com.commerce.mapper.cart.CartResponseMapper;
import com.commerce.model.entity.Cart;
import com.commerce.model.entity.CartItem;
import com.commerce.model.entity.ProductVariant;
import com.commerce.model.entity.User;
import com.commerce.model.response.cart.CartResponse;
import com.commerce.model.response.user.UserResponse;
import com.commerce.service.user.UserService;
import com.github.javafaker.Faker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartResponseMapper cartResponseMapper;

    @Mock
    private ProductVariantRepository productVariantRepository;

    @Mock
    private UserService userService;

    private String email;

    private User user;

    private UserResponse userResponse;

    private Faker faker;

    @BeforeEach
    void setup() {

        faker = new Faker();

        email = faker.internet().emailAddress();

        user = new User();
        user.setEmail(email);

        userResponse = new UserResponse();
        userResponse.setEmail(email);

    }

    @Test
    void it_should_add_to_cart_when_cart_is_empty() {

        // given
        Long productVariantId = faker.number().randomNumber();
        Integer amount = faker.number().randomDigitNotZero();

        Cart cart = new Cart();
        user.setCart(cart);
        cart.setUser(user);

        ProductVariant productVariant = new ProductVariant();
        productVariant.setPrice((double) faker.number().randomNumber());
        productVariant.setShippingPrice((double) faker.number().randomNumber());
        productVariant.setStock(amount + 1);

        CartResponse expected = new CartResponse();

        given(userService.getCurrentUser()).willReturn(userResponse);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(productVariantRepository.findById(productVariantId)).willReturn(Optional.of(productVariant));
        given(cartRepository.save(cart)).willReturn(cart);
        given(cartResponseMapper.apply(cart)).willReturn(expected);

        // when
        CartResponse actual = cartService.addToCart(productVariantId, amount);

        // then
        then(actual).isEqualTo(expected);

    }

    @Test
    void it_should_add_to_cart_when_cart_is_null() {

        // given
        Long productVariantId = faker.number().randomNumber();
        Integer amount = faker.number().randomDigitNotZero();

        Cart cart = new Cart();
        cart.setUser(user);

        ProductVariant productVariant = new ProductVariant();
        productVariant.setPrice((double) faker.number().randomNumber());
        productVariant.setShippingPrice((double) faker.number().randomNumber());
        productVariant.setStock(amount + 1);

        CartResponse expected = new CartResponse();

        given(userService.getCurrentUser()).willReturn(userResponse);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(productVariantRepository.findById(productVariantId)).willReturn(Optional.of(productVariant));
        given(cartRepository.save(any(Cart.class))).willReturn(cart);
        given(cartResponseMapper.apply(cart)).willReturn(expected);

        // when
        CartResponse actual = cartService.addToCart(productVariantId, amount);

        // then
        then(actual).isEqualTo(expected);

    }

    @Test
    void it_should_add_to_cart_when_cart_already_have_same_product() {

        // given
        ArgumentCaptor<Cart> cartArgumentCaptor = ArgumentCaptor.forClass(Cart.class);
        Long productVariantId = faker.number().randomNumber();
        Integer amount = faker.number().randomDigitNotZero();

        Cart cart = new Cart();

        user.setCart(cart);
        cart.setUser(user);

        ProductVariant productVariant = new ProductVariant();
        productVariant.setId(productVariantId);
        productVariant.setPrice((double) faker.number().randomNumber());
        productVariant.setShippingPrice((double) faker.number().randomNumber());
        productVariant.setStock(amount + 1);

        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setProductVariant(productVariant);
        cartItem.setQuantity(1);
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);

        CartResponse expected = new CartResponse();

        given(userService.getCurrentUser()).willReturn(userResponse);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(cartRepository.save(cart)).willReturn(cart);
        given(cartResponseMapper.apply(cart)).willReturn(expected);

        // when
        CartResponse cartResponseResult = cartService.addToCart(productVariantId, amount);

        // then
        BDDMockito.then(cartRepository).should().save(cartArgumentCaptor.capture());
        then(cartArgumentCaptor.getValue().getCartItems().size()).isEqualTo(1);
        then(cartArgumentCaptor.getValue().getCartItems().get(0).getProductVariant().getId())
                .isEqualTo(productVariantId);
        then(cartArgumentCaptor.getValue().getCartItems().get(0).getQuantity()).isEqualTo(amount + 1);

        then(cartResponseResult).isEqualTo(expected);

    }

    @Test
    void it_should_throw_exception_when_cart_already_have_product_and_no_stock() {

        // given
        Long productVariantId = faker.number().randomNumber();
        Integer amount = faker.number().randomDigitNotZero();

        Cart cart = new Cart();

        user.setCart(cart);
        cart.setUser(user);

        ProductVariant productVariant = new ProductVariant();
        productVariant.setId(productVariantId);
        productVariant.setPrice((double) faker.number().randomNumber());
        productVariant.setShippingPrice((double) faker.number().randomNumber());
        productVariant.setStock(amount + 1);

        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setProductVariant(productVariant);
        cartItem.setQuantity(productVariant.getStock() + amount);
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);

        given(userService.getCurrentUser()).willReturn(userResponse);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        // when, then

        thenThrownBy(() -> cartService.addToCart(productVariantId, amount)).isInstanceOf(InvalidArgumentException.class)
                .hasMessage("Product does not have desired stock.");

    }

    @Test
    void it_should_throw_exception_when_cart_is_null_and_no_stock() {

        // given
        Long productVariantId = faker.number().randomNumber();
        Integer amount = faker.number().randomDigitNotZero();

        ProductVariant productVariant = new ProductVariant();
        productVariant.setStock(amount - 1);

        given(userService.getCurrentUser()).willReturn(userResponse);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(productVariantRepository.findById(productVariantId)).willReturn(Optional.of(productVariant));

        // when, then

        thenThrownBy(() -> cartService.addToCart(productVariantId, amount)).isInstanceOf(InvalidArgumentException.class)
                .hasMessage("Product does not have desired stock.");

    }

    @Test
    void it_should_increment_cart_item() {

        // given
        ArgumentCaptor<Cart> cartArgumentCaptor = ArgumentCaptor.forClass(Cart.class);
        Long cartItemId = faker.number().randomNumber();
        Integer amount = faker.number().randomDigitNotZero();

        Cart cart = new Cart();
        user.setCart(cart);
        cart.setUser(user);

        ProductVariant productVariant = new ProductVariant();
        productVariant.setPrice((double) faker.number().randomNumber());
        productVariant.setShippingPrice((double) faker.number().randomNumber());
        productVariant.setStock(amount + 1);

        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemId);
        cartItem.setProductVariant(productVariant);
        cartItem.setQuantity(1);
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);

        CartResponse expected = new CartResponse();

        given(userService.getCurrentUser()).willReturn(userResponse);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(cartResponseMapper.apply(cart)).willReturn(expected);

        // when
        CartResponse actual = cartService.incrementCartItem(cartItemId, amount);

        // then
        BDDMockito.then(cartRepository).should().save(cartArgumentCaptor.capture());
        then(cartArgumentCaptor.getValue().getCartItems().get(0).getId()).isEqualTo(cartItemId);
        then(cartArgumentCaptor.getValue().getCartItems().size()).isEqualTo(1);
        then(cartArgumentCaptor.getValue().getCartItems().get(0).getQuantity()).isEqualTo(amount + 1);

        then(actual).isEqualTo(expected);

    }

    @Test
    void it_should_throw_exception_when_increment_and_no_stock() {

        // given
        Long cartItemId = faker.number().randomNumber();
        Integer amount = faker.number().randomDigitNotZero();

        Cart cart = new Cart();
        user.setCart(cart);
        cart.setUser(user);

        ProductVariant productVariant = new ProductVariant();
        productVariant.setStock(amount - 1);

        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemId);
        cartItem.setProductVariant(productVariant);
        cartItem.setQuantity(productVariant.getStock());
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);

        given(userService.getCurrentUser()).willReturn(userResponse);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        // when, then
        thenThrownBy(() -> cartService.incrementCartItem(cartItemId, amount))
                .isInstanceOf(InvalidArgumentException.class).hasMessage("Product does not have desired stock.");

    }

    @Test
    void it_should_throw_exception_when_increment_and_no_cart_item() {

        // given
        Long cartItemId = faker.number().randomNumber();
        Integer amount = faker.number().randomDigitNotZero();

        Cart cart = new Cart();
        user.setCart(cart);
        cart.setUser(user);
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemId + 1);
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);

        given(userService.getCurrentUser()).willReturn(userResponse);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        // when, then
        thenThrownBy(() -> cartService.incrementCartItem(cartItemId, amount))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("CartItem not found");

    }

    @Test
    void it_should_throw_exception_when_increment_and_no_cart() {

        // given
        Long cartItemId = faker.number().randomNumber();
        Integer amount = faker.number().randomDigitNotZero();

        given(userService.getCurrentUser()).willReturn(userResponse);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        // when, then
        thenThrownBy(() -> cartService.incrementCartItem(cartItemId, amount))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Empty cart");

    }

}
