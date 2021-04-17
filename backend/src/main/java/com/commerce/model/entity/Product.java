package com.commerce.model.entity;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.commerce.model.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Product extends BaseEntity {

	@ManyToOne(optional = false)
	@NotNull(message = "Product category is required")
	private ProductCategory productCategory;

	@OneToMany(mappedBy = "product")
	private List<ProductVariant> productVariants;

	@NotBlank(message = "Sku is required")
	private String sku;

	@NotBlank(message = "Product name is required")
	private String name;

	@NotBlank(message = "Product URL is required")
	private String url;

	@Lob
	@NotBlank(message = "Product description is required")
	private String description;

	private Instant createdAt;

	private Instant updatedAt;

	@PrePersist
	private void prePersist() {
		createdAt = Instant.now();
	}

	@PreUpdate
	private void PreUpdate() {
		updatedAt = Instant.now();
	}

}
