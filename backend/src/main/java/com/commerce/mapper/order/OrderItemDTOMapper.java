package com.commerce.mapper.order;

import java.util.function.Function;

import com.commerce.mapper.category.CategoryDTOMapper;
import com.commerce.mapper.color.ColorDTOMapper;
import com.commerce.model.dto.OrderItemDTO;
import com.commerce.model.entity.OrderItem;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class OrderItemDTOMapper implements Function<OrderItem, OrderItemDTO> {

    private final CategoryDTOMapper categoryDTOMapper;
    private final ColorDTOMapper colorDTOMapper;

    @Override
    public OrderItemDTO apply(OrderItem orderItem) {

        if (orderItem == null) {
            return null;
        }

        return OrderItemDTO.builder().url(orderItem.getProductVariant().getProduct().getUrl())
                .name(orderItem.getProductVariant().getProduct().getName())
                .price(orderItem.getProductVariant().getPrice())
                .shippingPrice((orderItem.getProductVariant().getShippingPrice()))
                .thumbURL(orderItem.getProductVariant().getThumbURL()).quantity(orderItem.getQuantity())
                .category(categoryDTOMapper.apply(orderItem.getProductVariant().getProduct().getProductCategory()))
                .color(colorDTOMapper.apply(orderItem.getProductVariant().getColor())).build();

    }

}
