package com.commerce.mapper.product;

import java.util.function.Function;

import com.commerce.model.entity.ProductVariant;
import com.commerce.model.response.product.ProductVariantResponse;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductVariantResponseMapper implements Function<ProductVariant, ProductVariantResponse> {

    private final ProductVariantDTOMapper productVariantDTOMapper;

    @Override
    public ProductVariantResponse apply(ProductVariant productVariant) {

        ProductVariantResponse productVariantResponse = new ProductVariantResponse();
        productVariantResponse.setId(productVariant.getId());
        productVariantResponse.setName(productVariant.getProduct().getName());
        productVariantResponse.setUrl(productVariant.getProduct().getUrl());
        productVariantResponse.setProductVariant(productVariantDTOMapper.apply(productVariant));

        return productVariantResponse;

    }

}
