package com.commerce.model.common;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Address extends BaseEntity {

	@Pattern(regexp = "^[a-zA-Z\\s]+$")
	private String country;

	@Pattern(regexp = "^[a-zA-Z\\s]+$")
	private String state;

	@Pattern(regexp = "^[a-zA-Z\\s]+$")
	private String city;

	@Pattern(regexp = "^[a-zA-Z\\s]+$")
	private String address;

	@Pattern(regexp = "^[0-9]+$")
	private String zip;

	@Pattern(regexp = "^[0-9]+$")
	@Size(min = 11, max = 12)
	private String phone;

}
