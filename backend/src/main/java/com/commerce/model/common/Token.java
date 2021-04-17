package com.commerce.model.common;

import java.time.Instant;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.commerce.model.entity.User;

@Embeddable
public class Token {

	private String token;

	@OneToOne(fetch = FetchType.EAGER)
	private User user;

	private Instant expiresAt;

}
