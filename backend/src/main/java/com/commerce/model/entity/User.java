package com.commerce.model.entity;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.commerce.model.common.Address;
import com.commerce.model.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends BaseEntity {
	
	@Email(message = "Please enter a valid email")
	private String email;

	@NotBlank
	@Size(min = 6, max = 52, message = "Password must be atleast 6 characters long")
	private String Password;

	@Pattern(regexp = "^[a-zA-z]+$")
	private String firstName;
	
	@Pattern(regexp = "^[a-zA-z]+$")
	private String lastName;

	@OneToOne
	private Address address;

	private Instant registeredAt;

	private Boolean isVerified;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Cart cart;

	@PrePersist
	private void prePersist() {
		registeredAt = Instant.now();
	}

}
