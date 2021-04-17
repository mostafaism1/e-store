package com.commerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OrderItemDTO {

    private String url;
    private String name;
    private Double price;
    private Double shippingPrice;
    private String thumbURL;
    private Integer quantity;
    private CategoryDTO category;
    private ColorDTO color;

}
