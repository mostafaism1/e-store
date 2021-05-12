package com.commerce.service.order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.commerce.dao.OrderRepository;
import com.commerce.dao.UserRepository;
import com.commerce.error.exception.InvalidArgumentException;
import com.commerce.error.exception.ResourceFetchException;
import com.commerce.mapper.common.AddressDTOToAddressMapper;
import com.commerce.mapper.order.OrderResponseMapper;
import com.commerce.model.entity.Cart;
import com.commerce.model.entity.Order;
import com.commerce.model.entity.OrderItem;
import com.commerce.model.entity.User;
import com.commerce.model.request.order.PostOrderRequest;
import com.commerce.model.response.order.OrderResponse;
import com.commerce.model.response.user.UserResponse;
import com.commerce.service.cart.CartService;
import com.commerce.service.user.UserService;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderResponseMapper orderResponseMapper;
    private final CartService cartService;
    private final AddressDTOToAddressMapper addressDTOToAddressMapper;

    @Override
    public Integer getAllOrdersCount() {

        User user = getCurrentUser();

        return orderRepository.countAllByUser(user)
                .orElseThrow(() -> new ResourceFetchException("An error occurred whilst fetching orders count"));

    }

    @Override
    public List<OrderResponse> getAllOrders(Integer page, Integer pageSize) {

        User user = getCurrentUser();

        PageRequest pageRequest = PageRequest.of(page, pageSize);

        return orderRepository.findAllByUserOrderByPlacedAtDesc(user, pageRequest).stream().map(orderResponseMapper)
                .collect(Collectors.toList());

    }

    @Override
    public OrderResponse postOrder(PostOrderRequest postOrderRequest) {

        User user = getCurrentUser();
        Cart cart = user.getCart();
        if (cart == null || cart.getCartItems() == null) {
            throw new InvalidArgumentException("Cart is not valid");
        }

        checkStock(cart);

        Order order = createOrder(postOrderRequest, user, cart);

        Order savedOrder = orderRepository.save(order);

        cartService.emptyCart();

        return orderResponseMapper.apply(savedOrder);

    }

    private Order createOrder(PostOrderRequest postOrderRequest, User user, Cart cart) {
        final Order order = new Order();
        order.setOrderItems(new ArrayList<>());
        cart.getCartItems().forEach(cartItem -> {
            cartItem.getProductVariant()
                    .setSellCount(cartItem.getProductVariant().getSellCount() + cartItem.getQuantity());

            OrderItem orderItem = new OrderItem();
            orderItem.setProductVariant(cartItem.getProductVariant());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.add(orderItem);
            order.setOrderItems(orderItems);
        });
        order.setUser(user);
        order.setDiscount(cart.getDiscount());
        order.setBillingAddress(addressDTOToAddressMapper.apply(postOrderRequest.getBillingAddress()));
        order.setShippingAddress(addressDTOToAddressMapper.apply(postOrderRequest.getShippingAddress()));
        order.setShippingCompany(postOrderRequest.getShippingCompany());
        return order;
    }

    private void checkStock(Cart cart) {
        if (cart.getCartItems().stream()
                .anyMatch(cartItem -> cartItem.getQuantity() > cartItem.getProductVariant().getStock())) {
            throw new InvalidArgumentException("A product in your cart is out of stock.");
        }
    }

    private User getCurrentUser() {
        UserResponse currentUser = userService.getCurrentUser();
        User user = userRepository.findByEmail(currentUser.getEmail()).get();
        return user;
    }

}
