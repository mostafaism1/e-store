package com.commerce.mapper.common;

import java.util.function.Function;

import com.commerce.model.common.ShippingInformation;
import com.commerce.model.dto.ShippingInformationDTO;

import org.springframework.stereotype.Component;

@Component
public class ShippingInformationDTOMapper implements Function<ShippingInformation, ShippingInformationDTO> {

    @Override
    public ShippingInformationDTO apply(ShippingInformation shippingInformation) {

        if (shippingInformation == null) {
            return null;
        }

        return ShippingInformationDTO.builder().length(shippingInformation.getLength())
                .width(shippingInformation.getWidth()).height(shippingInformation.getHeight())
                .weight(shippingInformation.getWeight()).build();

    }

}
