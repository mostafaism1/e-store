package com.commerce.model.common;

import javax.persistence.Embeddable;
import javax.validation.constraints.Min;

@Embeddable
public class ShippingInformation {
	
	@Min(value = 0, message = "Length must be positive")
	private Double length;

	@Min(value = 0, message = "Width must be positive")
	private Double width;

	@Min(value = 0, message = "Height must be positive")
	private Double height;
	
	@Min(value = 0, message = "Weight must be positive")
	private Double weight;

}
