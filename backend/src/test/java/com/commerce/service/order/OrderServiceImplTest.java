package com.commerce.service.order;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.commerce.dao.OrderRepository;
import com.commerce.dao.UserRepository;
import com.commerce.error.exception.ResourceFetchException;
import com.commerce.mapper.order.OrderResponseMapper;
import com.commerce.model.entity.Order;
import com.commerce.model.entity.User;
import com.commerce.model.response.order.OrderResponse;
import com.commerce.model.response.user.UserResponse;
import com.commerce.service.user.UserService;
import com.github.javafaker.Faker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

}
