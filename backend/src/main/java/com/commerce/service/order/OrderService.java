package com.commerce.service.order;

import java.util.List;

import com.commerce.model.request.order.PostOrderRequest;
import com.commerce.model.response.order.OrderResponse;

public interface OrderService {

    Integer getAllOrdersCount();

    List<OrderResponse> getAllOrders(Integer page, Integer pageSize);

    OrderResponse postOrder(PostOrderRequest postOrderRequest);

}