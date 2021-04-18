package com.commerce.model.response.product;

import com.commerce.model.dto.ProductVariantDTO;

import lombok.Data;

@Data
public class ProductVariantResponse {

    private Long id;
    private String name;
    private String url;
    private ProductVariantDTO productVariant;
    
}
