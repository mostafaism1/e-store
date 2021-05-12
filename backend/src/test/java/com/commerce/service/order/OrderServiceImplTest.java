package com.commerce.service.order;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.commerce.dao.OrderRepository;
import com.commerce.dao.UserRepository;
import com.commerce.error.exception.InvalidArgumentException;
import com.commerce.error.exception.ResourceFetchException;
import com.commerce.mapper.common.AddressDTOToAddressMapper;
import com.commerce.mapper.order.OrderResponseMapper;
import com.commerce.model.entity.Cart;
import com.commerce.model.entity.CartItem;
import com.commerce.model.entity.Order;
import com.commerce.model.entity.ProductVariant;
import com.commerce.model.entity.User;
import com.commerce.model.request.order.PostOrderRequest;
import com.commerce.model.response.order.OrderResponse;
import com.commerce.model.response.user.UserResponse;
import com.commerce.service.cart.CartService;
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
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderResponseMapper orderResponseMapper;

    @Mock
    private AddressDTOToAddressMapper addressDTOToAddressMapper;

    @Mock
    private CartService cartService;

    private Faker faker;

    private String email;

    private User user;

    private UserResponse userResponse;

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
    void it_should_get_all_orders_count() {

        // given
        List<Order> orders = Stream.generate(Order::new).limit(faker.number().randomDigitNotZero())
                .collect(Collectors.toList());

        given(userService.getCurrentUser()).willReturn(userResponse);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(orderRepository.countAllByUser(user)).willReturn(Optional.of(orders.size()));

        // when
        Integer ordersCount = orderService.getAllOrdersCount();

        // then
        then(ordersCount).isEqualTo(orders.size());

    }

    @Test
    void it_should_throw_exception_when_error() {

        // given
        given(userService.getCurrentUser()).willReturn(userResponse);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(orderRepository.countAllByUser(user)).willReturn(Optional.empty());

        // when, then
        thenThrownBy(() -> orderService.getAllOrdersCount()).isInstanceOf(ResourceFetchException.class)
                .hasMessage("An error occurred whilst fetching orders count");

    }

    @Test
    void it_should_get_all_orders() {

        // given
        Integer page = faker.number().randomDigitNotZero();
        Integer pageSize = faker.number().randomDigitNotZero();

        List<Order> orders = Stream.generate(Order::new).limit(faker.number().randomDigitNotZero())
                .collect(Collectors.toList());

        OrderResponse orderResponse = new OrderResponse();

        given(userService.getCurrentUser()).willReturn(userResponse);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(orderRepository.findAllByUserOrderByPlacedAtDesc(user, PageRequest.of(page, pageSize)))
                .willReturn(orders);
        given(orderResponseMapper.apply(any(Order.class))).willReturn(orderResponse);

        // when
        List<OrderResponse> actual = orderService.getAllOrders(page, pageSize);

        // then
        then(actual.size()).isEqualTo(orders.size());
        actual.forEach(orderResponse1 -> then(orderResponse1).isEqualTo(orderResponse));

    }

    @Test
    void it_should_post_an_order() {

        // given
        ProductVariant productVariant = new ProductVariant();
        productVariant.setSellCount(faker.number().randomDigitNotZero());
        productVariant.setStock(faker.number().randomDigitNotZero());
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setProductVariant(productVariant);
        cartItem.setQuantity(productVariant.getStock() - 1);
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);

        user.setCart(cart);

        PostOrderRequest postOrderRequest = new PostOrderRequest();

        OrderResponse expected = new OrderResponse();

        Order order = new Order();

        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);

        given(userService.getCurrentUser()).willReturn(userResponse);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(orderRepository.save(any(Order.class))).willReturn(order);
        given(orderResponseMapper.apply(order)).willReturn(expected);

        // when
        OrderResponse actual = orderService.postOrder(postOrderRequest);

        // then
        BDDMockito.then(orderRepository).should().save(orderArgumentCaptor.capture());
        then(orderArgumentCaptor.getValue().getUser()).isEqualTo(user);
        then(orderArgumentCaptor.getValue().getOrderItems().size()).isEqualTo(1);
        then(orderArgumentCaptor.getValue().getOrderItems().get(0).getProductVariant()).isEqualTo(productVariant);
        then(orderArgumentCaptor.getValue().getOrderItems().get(0).getQuantity())
                .isEqualTo(productVariant.getStock() - 1);

        then(actual).isEqualTo(expected);

    }

    @Test
    void it_should_throw_exception_when_post_order_has_null_cart() {

        // given
        given(userService.getCurrentUser()).willReturn(userResponse);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        // when, then
        thenThrownBy(() -> orderService.postOrder(new PostOrderRequest())).isInstanceOf(InvalidArgumentException.class)
                .hasMessage("Cart is not valid");

    }

    @Test
    void it_should_throw_exception_when_post_order_has_out_of_stock_item() {

        // given
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        ProductVariant productVariant = new ProductVariant();
        productVariant.setStock(faker.number().randomDigitNotZero());
        cartItem.setProductVariant(productVariant);
        cartItem.setQuantity(productVariant.getStock() + faker.number().randomDigitNotZero());

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);

        user.setCart(cart);

        given(userService.getCurrentUser()).willReturn(userResponse);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        // when, then
        thenThrownBy(() -> orderService.postOrder(new PostOrderRequest())).isInstanceOf(InvalidArgumentException.class)
                .hasMessage("A product in your cart is out of stock.");

    }

}
