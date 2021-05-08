package com.commerce.service.token;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import com.commerce.dao.PasswordForgotTokenRepository;
import com.commerce.dao.UserRepository;
import com.commerce.dao.VerificationTokenRepository;
import com.commerce.error.exception.InvalidArgumentException;
import com.commerce.error.exception.ResourceNotFoundException;
import com.commerce.model.common.Token;
import com.commerce.model.entity.PasswordForgotToken;
import com.commerce.model.entity.User;
import com.commerce.model.entity.VerificationToken;
import com.commerce.model.event.OnPasswordForgotRequestEvent;
import com.commerce.model.event.OnRegistrationCompleteEvent;
import com.commerce.model.request.user.PasswordForgotValidateRequest;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final Duration EXPIRES_AFTER = Duration.ofDays(1);

    private final VerificationTokenRepository verificationTokenRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;
    private final PasswordForgotTokenRepository passwordForgotTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createEmailConfirmToken(User user) {

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(generateTokenForUser(user));

        verificationTokenRepository.save(verificationToken);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, verificationToken.getToken().getToken()));

    }

    @Override
    public void validateEmail(String token) {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Null verification token"));

        if (isTokenExpired(verificationToken.getToken())) {
            throw new InvalidArgumentException("Token is expired");
        }

        User user = verificationToken.getToken().getUser();
        user.setIsVerified(true);
        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);

    }

    @Override
    public void createPasswordResetToken(String email) {

        User user = userRepository.findByEmail(email).get();
        if (user == null) {
            return;
        }

        PasswordForgotToken passwordForgotToken = passwordForgotTokenRepository.findByTokenUser(user)
                .orElse(new PasswordForgotToken());

        passwordForgotToken.setToken(generateTokenForUser(user));

        passwordForgotTokenRepository.save(passwordForgotToken);

        eventPublisher.publishEvent(new OnPasswordForgotRequestEvent(user, passwordForgotToken.getToken().getToken()));

    }

    @Override
    public void validateForgotPasswordConfirm(String token) {

        PasswordForgotToken passwordForgotToken = passwordForgotTokenRepository.findByTokenToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        if (isTokenExpired(passwordForgotToken.getToken())) {
            throw new InvalidArgumentException("Token is expired");
        }

    }

    @Override
    public void validateForgotPassword(PasswordForgotValidateRequest passwordForgotValidateRequest) {

        PasswordForgotToken passwordForgotToken = passwordForgotTokenRepository
                .findByTokenToken(passwordForgotValidateRequest.getToken())
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        if (isTokenExpired(passwordForgotToken.getToken())) {
            throw new InvalidArgumentException("Token is expired");
        }

        User user = passwordForgotToken.getToken().getUser();
        passwordEncoder.matches(passwordForgotValidateRequest.getPassword(), user.getPassword());
        user.setPassword(passwordEncoder.encode(passwordForgotValidateRequest.getPassword()));
        userRepository.save(user);

        passwordForgotTokenRepository.delete(passwordForgotToken);

    }

    private Instant calculateExpiryDate() {
        return Instant.now().plus(EXPIRES_AFTER);
    }

    private Token generateTokenForUser(User user) {

        String tokenString = generateToken();
        Token token = new Token();
        token.setToken(tokenString);
        token.setUser(user);
        token.setExpiresAt(calculateExpiryDate());

        return token;

    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private boolean isTokenExpired(Token token) {
        return token.getExpiresAt().isBefore(Instant.now());
    }

}
