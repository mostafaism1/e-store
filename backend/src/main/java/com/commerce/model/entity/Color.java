package com.commerce.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.commerce.model.common.BaseEntity;
import com.commerce.validation.HexColor;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Color extends BaseEntity {

	@OneToMany(mappedBy = "color")
	private List<ProductVariant> productVariants;

	@NotBlank(message = "Color name is required")
	private String name;

	@HexColor
	private String hex;

}
