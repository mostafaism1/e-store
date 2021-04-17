package com.commerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductVariantDTO {

    private Long id;
    private Double price;
    private String thumbURL;
    private Integer stock;
    private ColorDTO color;

}
