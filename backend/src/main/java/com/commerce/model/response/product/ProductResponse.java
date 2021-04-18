package com.commerce.model.response.product;

import java.util.List;

import com.commerce.model.dto.ProductVariantDTO;

import lombok.Data;

@Data
public class ProductResponse {

    private String name;
    private String url;
    private List<ProductVariantDTO> productVariants;

}
