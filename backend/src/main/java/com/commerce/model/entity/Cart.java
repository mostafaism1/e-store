package com.commerce.model.entity;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.commerce.model.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Cart extends BaseEntity {

	@OneToOne(optional = false)
	private User user;

	@OneToMany(mappedBy = "cart")
	private List<CartItem> cartItems;

	@ManyToOne
	private Discount discount;

	private Instant createdAt;

	@PrePersist
	private void prePersist() {
		createdAt = Instant.now();
	}

}
