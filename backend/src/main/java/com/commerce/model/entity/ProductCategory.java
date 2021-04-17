package com.commerce.model.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import com.commerce.model.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ProductCategory extends BaseEntity {

	@NotBlank(message = "Category name is required")
	private String name;

}
