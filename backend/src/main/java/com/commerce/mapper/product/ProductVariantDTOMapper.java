package com.commerce.mapper.product;

import java.util.function.Function;

import com.commerce.mapper.color.ColorDTOMapper;
import com.commerce.model.dto.ProductVariantDTO;
import com.commerce.model.entity.ProductVariant;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductVariantDTOMapper implements Function<ProductVariant, ProductVariantDTO> {

    private final ColorDTOMapper colorDTOMapper;

    @Override
    public ProductVariantDTO apply(ProductVariant productVariant) {

        if (productVariant == null) {
            return null;
        }

        return ProductVariantDTO.builder().id(productVariant.getId()).price(productVariant.getPrice())
                .thumbURL(productVariant.getThumbURL()).stock(productVariant.getStock())
                .color(colorDTOMapper.apply(productVariant.getColor())).build();

    }

}
