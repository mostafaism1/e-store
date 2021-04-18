package com.commerce.mapper.order;

import java.util.function.Function;
import java.util.stream.Collectors;

import com.commerce.mapper.common.AddressDTOMapper;
import com.commerce.mapper.discount.DiscountDTOMapper;
import com.commerce.model.entity.Order;
import com.commerce.model.response.order.OrderResponse;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class OrderResponseMapper implements Function<Order, OrderResponse> {

    private final AddressDTOMapper addressDTOMapper;
    private final DiscountDTOMapper discountDTOMapper;
    private final OrderItemDTOMapper orderItemDTOMapper;

    @Override
    public OrderResponse apply(Order order) {

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setBillingAddress(addressDTOMapper.apply(order.getBillingAddress()));
        orderResponse.setShippingAddress(addressDTOMapper.apply(order.getShippingAddress()));
        orderResponse.setShippingCompany(order.getShippingCompany());
        orderResponse.setOrderItems(order.getOrderItems().stream().map(orderItem -> orderItemDTOMapper.apply(orderItem))
                .collect(Collectors.toList()));
        orderResponse.setPlacedAt(order.getPlacedAt());
        orderResponse.setDiscount(discountDTOMapper.apply(order.getDiscount()));
        orderResponse.setTotalPrice(order.getTotalPrice());
        orderResponse.setTotalShippingPrice(order.getTotalShippingPrice());
        orderResponse.setIsDelivered(order.getIsDelivered());

        return orderResponse;

    }

}
