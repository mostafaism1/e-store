package com.commerce.model.response.product;

import java.util.List;

import com.commerce.model.dto.CategoryDTO;
import com.commerce.model.dto.ProductVariantDetailDTO;

import lombok.Data;

@Data
public class ProductDetailsResponse {

    private String name;
    private String url;
    private String sku;
    private String description;
    private CategoryDTO category;
    private List<ProductVariantDetailDTO> productVariantDetails;

}
