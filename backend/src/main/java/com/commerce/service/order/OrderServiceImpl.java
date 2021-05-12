package com.commerce.service.order;

import java.util.List;

import com.commerce.dao.OrderRepository;
import com.commerce.dao.UserRepository;
import com.commerce.error.exception.ResourceFetchException;
import com.commerce.model.entity.User;
import com.commerce.model.request.order.PostOrderRequest;
import com.commerce.model.response.order.OrderResponse;
import com.commerce.model.response.user.UserResponse;
import com.commerce.service.user.UserService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public Integer getAllOrdersCount() {

        User user = getCurrentUser();

        return orderRepository.countAllByUser(user)
                .orElseThrow(() -> new ResourceFetchException("An error occurred whilst fetching orders count"));

    }

    @Override
    public List<OrderResponse> getAllOrders(Integer page, Integer pageSize) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrderResponse postOrder(PostOrderRequest postOrderRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    private User getCurrentUser() {
        UserResponse currentUser = userService.getCurrentUser();
        User user = userRepository.findByEmail(currentUser.getEmail()).get();
        return user;
    }

}
