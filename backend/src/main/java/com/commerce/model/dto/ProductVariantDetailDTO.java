package com.commerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductVariantDetailDTO {

    private Long id;
    private ShippingInformationDTO shippingInformation;
    private String composition;
    private Double price;
    private Double shippingPrice;
    private Double taxPercent;
    private String imageURL;
    private String thumbURL;
    private Integer stock;
    private ColorDTO color;
    private Boolean isActive;

}
