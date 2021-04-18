package com.commerce.model.entity;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import com.commerce.model.common.Address;
import com.commerce.model.common.BaseEntity;

import org.springframework.data.annotation.Transient;

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

	@Pattern(regexp = "^[a-zA-Z\\s]+$")
	private String shippingCompany;

	private Boolean isDelivered;

	@PrePersist
	private void prePersist() {
		placedAt = Instant.now();
		isDelivered = false;
	}

	@Transient
	public Double getTotalPrice() {

		Double itemsPrice = orderItems.stream()
				.map(orderItem -> orderItem.getProductVariant().getPrice() * orderItem.getQuantity())
				.reduce(0D, Double::sum);

		Double totalShippingPrice = getTotalShippingPrice();

		Double discountedPrice = (itemsPrice + totalShippingPrice) * (1 - (100.0 - discount.getDiscountPercent()) / 100);

		return discountedPrice;

	}

	@Transient
	public Double getTotalShippingPrice() {

		return orderItems.stream()
				.map(orderItem -> orderItem.getProductVariant().getShippingPrice() * orderItem.getQuantity())
				.reduce(0D, Double::sum);

	}

}
