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
public class CartItem extends BaseEntity {

	@ManyToOne(optional = false)
	private Cart cart;

	@ManyToOne
	private ProductVariant productVariant;

	private Integer quantity;

}
