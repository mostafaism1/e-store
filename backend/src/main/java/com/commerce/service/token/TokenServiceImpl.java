package com.commerce.service.token;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import com.commerce.dao.VerificationTokenRepository;
import com.commerce.model.common.Token;
import com.commerce.model.entity.User;
import com.commerce.model.entity.VerificationToken;
import com.commerce.model.event.OnRegistrationCompleteEvent;
import com.commerce.model.request.user.PasswordForgotValidateRequest;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final Duration EXPIRES_AFTER = Duration.ofDays(1);

    private final VerificationTokenRepository verificationTokenRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void createEmailConfirmToken(User user) {

        VerificationToken verificationToken = generateTokenForUser(user);

        verificationTokenRepository.save(verificationToken);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, verificationToken.getToken().getToken()));

    }

    @Override
    public void validateEmail(String token) {
        // TODO Auto-generated method stub

    }

    @Override
    public void createPasswordResetToken(String email) {
        // TODO Auto-generated method stub

    }

    @Override
    public void validateForgotPasswordConfirm(String token) {
        // TODO Auto-generated method stub

    }

    @Override
    public void validateForgotPassword(PasswordForgotValidateRequest passwordForgotValidateRequest) {
        // TODO Auto-generated method stub

    }

    private Instant calculateExpiryDate() {
        return Instant.now().plus(EXPIRES_AFTER);
    }

    private VerificationToken generateTokenForUser(User user) {
        String tokenString = generateToken();
        VerificationToken verificationToken = new VerificationToken();
        Token token = new Token();
        token.setToken(tokenString);
        token.setUser(user);
        token.setExpiresAt(calculateExpiryDate());
        verificationToken.setToken(token);
        return verificationToken;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

}
