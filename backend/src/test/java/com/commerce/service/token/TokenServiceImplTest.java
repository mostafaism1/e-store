package com.commerce.service.token;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

import java.util.Optional;

import com.commerce.dao.PasswordForgotTokenRepository;
import com.commerce.dao.UserRepository;
import com.commerce.dao.VerificationTokenRepository;
import com.commerce.model.entity.PasswordForgotToken;
import com.commerce.model.entity.User;
import com.commerce.model.entity.VerificationToken;
import com.commerce.model.event.OnPasswordForgotRequestEvent;
import com.commerce.model.event.OnRegistrationCompleteEvent;
import com.commerce.service.user.UserService;
import com.github.javafaker.Faker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

        @InjectMocks
        private TokenServiceImpl tokenService;

        @Mock
        private UserService userService;

        @Mock
        private PasswordEncoder passwordEncoder;

        @Mock
        private ApplicationEventPublisher eventPublisher;

        @Mock
        private VerificationTokenRepository verificationTokenRepository;

        @Mock
        private PasswordForgotTokenRepository passwordForgotTokenRepository;

        @Mock
        private UserRepository userRepository;

        private User user;

        private Faker faker;

        @BeforeEach
        public void setUp() {
                user = new User();
                faker = new Faker();
        }

        @Test
        void it_should_create_email_confirm_token() {

                // given
                ArgumentCaptor<VerificationToken> verificationTokenArgumentCaptor = ArgumentCaptor
                                .forClass(VerificationToken.class);
                ArgumentCaptor<OnRegistrationCompleteEvent> onRegistrationCompleteEventArgumentCaptor = ArgumentCaptor
                                .forClass(OnRegistrationCompleteEvent.class);

                given(verificationTokenRepository.save(any(VerificationToken.class)))
                                .willReturn(new VerificationToken());

                // when
                tokenService.createEmailConfirmToken(user);

                // then
                BDDMockito.then(verificationTokenRepository).should(times(1))
                                .save(verificationTokenArgumentCaptor.capture());
                BDDMockito.then(eventPublisher).should(times(1))
                                .publishEvent(onRegistrationCompleteEventArgumentCaptor.capture());

                then(user).isEqualTo(onRegistrationCompleteEventArgumentCaptor.getValue().getUser());
                then(verificationTokenArgumentCaptor.getValue().getToken().getToken())
                                .isEqualTo(onRegistrationCompleteEventArgumentCaptor.getValue().getToken());

        }

        @Test
        void it_should_create_password_reset_token_if_it_does_not_exist() {

                // given
                String email = faker.internet().emailAddress();
                ArgumentCaptor<PasswordForgotToken> passwordForgotTokenArgumentCaptor = ArgumentCaptor
                                .forClass(PasswordForgotToken.class);
                ArgumentCaptor<OnPasswordForgotRequestEvent> onPasswordForgotRequestEventArgumentCaptor = ArgumentCaptor
                                .forClass(OnPasswordForgotRequestEvent.class);

                given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
                given(passwordForgotTokenRepository.findByTokenUser(user)).willReturn(Optional.empty());
                given(passwordForgotTokenRepository.save(any(PasswordForgotToken.class)))
                                .willReturn(new PasswordForgotToken());

                // when
                tokenService.createPasswordResetToken(email);

                // then
                BDDMockito.then(passwordForgotTokenRepository).should(times(1))
                                .save(passwordForgotTokenArgumentCaptor.capture());
                BDDMockito.then(eventPublisher).should(times(1))
                                .publishEvent(onPasswordForgotRequestEventArgumentCaptor.capture());

                then(user).isEqualTo(onPasswordForgotRequestEventArgumentCaptor.getValue().getUser());
                then(passwordForgotTokenArgumentCaptor.getValue().getToken().getToken())
                                .isEqualTo(onPasswordForgotRequestEventArgumentCaptor.getValue().getToken());

        }

        @Test
        void it_should_create_password_reset_token_if_it_already_exists() {

                // given
                String email = faker.internet().emailAddress();
                ArgumentCaptor<PasswordForgotToken> passwordForgotTokenArgumentCaptor = ArgumentCaptor
                                .forClass(PasswordForgotToken.class);
                ArgumentCaptor<OnPasswordForgotRequestEvent> onPasswordForgotRequestEventArgumentCaptor = ArgumentCaptor
                                .forClass(OnPasswordForgotRequestEvent.class);

                given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
                given(passwordForgotTokenRepository.findByTokenUser(user))
                                .willReturn(Optional.of(new PasswordForgotToken()));
                given(passwordForgotTokenRepository.save(any(PasswordForgotToken.class)))
                                .willReturn(new PasswordForgotToken());

                // when
                tokenService.createPasswordResetToken(email);

                // then
                BDDMockito.then(passwordForgotTokenRepository).should(times(1))
                                .save(passwordForgotTokenArgumentCaptor.capture());
                BDDMockito.then(eventPublisher).should(times(1))
                                .publishEvent(onPasswordForgotRequestEventArgumentCaptor.capture());

                then(user).isEqualTo(onPasswordForgotRequestEventArgumentCaptor.getValue().getUser());
                then(passwordForgotTokenArgumentCaptor.getValue().getToken().getToken())
                                .isEqualTo(onPasswordForgotRequestEventArgumentCaptor.getValue().getToken());

        }
}