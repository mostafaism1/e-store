package com.commerce.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.commerce.model.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OrderItem extends BaseEntity {

	@ManyToOne(optional = false)
	private Order order;

	@ManyToOne(optional = false)
	private ProductVariant productVariant;

	private Integer quantity;

}
