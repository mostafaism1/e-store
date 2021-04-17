package com.commerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CartItemDTO {

    private Long id;
    private String url;
    private String name;
    private Double price;
    private Integer quantity;
    private String thumbURL;
    private Integer stock;
    private ColorDTO color;

}
