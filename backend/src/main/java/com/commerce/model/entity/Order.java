package com.commerce.model.entity;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.commerce.model.common.Address;
import com.commerce.model.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Order extends BaseEntity {

	@ManyToOne(optional = false)
	private User user;

	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems;

	@OneToOne
	private Discount discount;

	@ManyToOne(optional = false)
	private Address shippingAddress;

	@ManyToOne(optional = false)
	private Address billingAddress;

	private Instant placedAt;

	private String shippingCompany;

	private Boolean isDelivered;

	@PrePersist
	private void prePersist() {
		placedAt = Instant.now();
		isDelivered = false;
	}

}
