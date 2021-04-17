package com.commerce.model.common;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Address extends BaseEntity {

	@NotBlank(message = "Country is required")
	private String country;

	@NotBlank(message = "State is required")
	private String state;

	@NotBlank(message = "State is required")
	private String city;

	@NotBlank(message = "Address is required")
	private String address;

	@NotBlank(message = "Zip is required")
	private String zip;

	@NotBlank(message = "Phone is required")
	private String phone;

}
