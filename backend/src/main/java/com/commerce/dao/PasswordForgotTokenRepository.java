package com.commerce.dao;

import java.util.Optional;

import com.commerce.model.entity.PasswordForgotToken;
import com.commerce.model.entity.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordForgotTokenRepository extends CrudRepository<PasswordForgotToken, Long> {

    Optional<PasswordForgotToken> findByTokenToken(String token);

    Optional<PasswordForgotToken> findByTokenUser(User user);

}
