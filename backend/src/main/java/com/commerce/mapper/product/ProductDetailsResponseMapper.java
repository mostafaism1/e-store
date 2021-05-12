package com.commerce.mapper.product;

import java.util.function.Function;
import java.util.stream.Collectors;

import com.commerce.mapper.category.CategoryDTOMapper;
import com.commerce.model.entity.Product;
import com.commerce.model.response.product.ProductDetailsResponse;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductDetailsResponseMapper implements Function<Product, ProductDetailsResponse> {

    private final CategoryDTOMapper categoryDTOMapper;
    private final ProductVariantDetailDTOMapper productVariantDetailDTOMapper;

    @Override
    public ProductDetailsResponse apply(Product product) {

        if (product == null) {
            return null;
        }

        ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
        productDetailsResponse.setName(product.getName());
        productDetailsResponse.setUrl(product.getUrl());
        productDetailsResponse.setSku(product.getSku());
        productDetailsResponse.setDescription(product.getDescription());
        productDetailsResponse.setCategory(categoryDTOMapper.apply(product.getProductCategory()));
        productDetailsResponse.setProductVariantDetails(product.getProductVariants().stream()
                .map(productVariant -> productVariantDetailDTOMapper.apply(productVariant))
                .collect(Collectors.toList()));

        return productDetailsResponse;

    }

}
