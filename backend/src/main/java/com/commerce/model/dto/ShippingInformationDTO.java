package com.commerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ShippingInformationDTO {

    private Double length;
    private Double width;
    private Double height;
    private Double weight;

}
