package com.commerce.mapper.product;

import java.util.function.Function;

import com.commerce.mapper.color.ColorDTOMapper;
import com.commerce.mapper.common.ShippingInformationDTOMapper;
import com.commerce.model.dto.ProductVariantDetailDTO;
import com.commerce.model.entity.ProductVariant;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductVariantDetailDTOMapper implements Function<ProductVariant, ProductVariantDetailDTO> {

    private final ShippingInformationDTOMapper shippingInformationDTOMapper;
    private final ColorDTOMapper colorDTOMapper;

    @Override
    public ProductVariantDetailDTO apply(ProductVariant productVariant) {

        if (productVariant == null) {
            return null;
        }

        return ProductVariantDetailDTO.builder().id(productVariant.getId())
                .shippingInformation(shippingInformationDTOMapper.apply(productVariant.getShippingInformation()))
                .composition(productVariant.getComposition()).price(productVariant.getPrice())
                .shippingPrice(productVariant.getShippingPrice()).taxPercent(productVariant.getTaxPercent())
                .imageURL(productVariant.getImageURL()).thumbURL(productVariant.getThumbURL())
                .stock(productVariant.getStock()).color(colorDTOMapper.apply(productVariant.getColor())).build();

    }

}
