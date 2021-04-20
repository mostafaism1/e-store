package com.commerce.mapper.product;

import java.util.function.Function;
import java.util.stream.Collectors;

import com.commerce.model.entity.Product;
import com.commerce.model.response.product.ProductResponse;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductResponseMapper implements Function<Product, ProductResponse> {

    private final ProductVariantDTOMapper productVariantDTOMapper;

    @Override
    public ProductResponse apply(Product product) {

        if (product == null) {
            return null;
        }

        ProductResponse productResponse = new ProductResponse();
        productResponse.setName(product.getName());
        productResponse.setUrl(product.getUrl());
        productResponse.setProductVariants(product.getProductVariants().stream()
                .map(productVariant -> productVariantDTOMapper.apply(productVariant)).collect(Collectors.toList()));

        return productResponse;

    }

}
