package com.commerce.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.commerce.model.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Discount extends BaseEntity {

	@OneToMany(mappedBy = "discount")
	private List<Order> orders;

	@OneToMany(mappedBy = "discount")
	private List<Cart> carts;

	private String code;

	private Integer discountPercent;

	private Boolean isActive;
}
