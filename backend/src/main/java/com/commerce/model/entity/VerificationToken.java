package com.commerce.model.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import com.commerce.model.common.BaseEntity;
import com.commerce.model.common.Token;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class VerificationToken extends BaseEntity {

	@Embedded
	private Token token;

}
