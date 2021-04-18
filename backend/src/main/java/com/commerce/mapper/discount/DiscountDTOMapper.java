package com.commerce.mapper.discount;

import java.util.function.Function;

import com.commerce.model.dto.DiscountDTO;
import com.commerce.model.entity.Discount;

import org.springframework.stereotype.Component;

@Component
public class DiscountDTOMapper implements Function<Discount, DiscountDTO> {

    @Override
    public DiscountDTO apply(Discount discount) {

        return DiscountDTO.builder().discountPercent(discount.getDiscountPercent()).isActive(discount.getIsActive())
                .build();

    }

}
