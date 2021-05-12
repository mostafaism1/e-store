package com.commerce.mapper.color;

import java.util.function.Function;

import com.commerce.model.entity.Color;
import com.commerce.model.response.color.ProductColorResponse;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ColorResponseMapper implements Function<Color, ProductColorResponse> {

    private final ColorDTOMapper colorDTOMapper;

    @Override
    public ProductColorResponse apply(Color color) {

        ProductColorResponse productColorResponse = new ProductColorResponse();
        productColorResponse.setColor(colorDTOMapper.apply(color));

        return productColorResponse;

    }

}
