package com.commerce.model.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

import com.commerce.model.common.BaseEntity;
import com.commerce.model.common.ShippingInformation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ProductVariant extends BaseEntity {

	@ManyToOne(optional = false)
	private Product product;

	@ManyToOne
	private Color color;

	@Embedded
	private ShippingInformation shippingInformation;

	private String composition;

	@Min(value = 0, message = "Price must be a positive number")
	private Double price;

	@Min(value = 0, message = "Price must be a positive number")
	private Double shippingPrice;

	@Min(value = 0, message = "Tax percent must be a positive number")
	private Double taxPercent;

	private String imageURL;

	private String thumbURL;

	@Min(value = 0, message = "Stock must be a positive number")
	private Integer stock;

	private Integer sellCount;

	private Boolean isActive;

}
