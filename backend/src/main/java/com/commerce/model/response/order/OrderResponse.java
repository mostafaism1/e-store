package com.commerce.model.response.order;

import java.time.Instant;
import java.util.List;

import com.commerce.model.dto.AddressDTO;
import com.commerce.model.dto.DiscountDTO;
import com.commerce.model.dto.OrderItemDTO;

import lombok.Data;

@Data
public class OrderResponse {

    private Long id;

    private AddressDTO billingAddress;

    private AddressDTO shippingAddress;

    private String shippingCompany;

    private List<OrderItemDTO> orderItems;    

    private Instant placedAt;

    private DiscountDTO discount;

    private Double totalShippingPrice;

    private Double totalPrice;

    private Boolean isDelivered;

}
