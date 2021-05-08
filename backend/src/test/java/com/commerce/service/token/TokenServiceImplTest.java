package com.commerce.service.token;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

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

        @Test
        void it_should_validate_email_by_token() {

                // given
                String tokenString = faker.random().hex();
                VerificationToken verificationToken = new VerificationToken();
                Token token = new Token();
                token.setToken(tokenString);
                token.setUser(user);
                token.setExpiresAt(Instant.now().plus(Duration.ofHours(faker.number().randomDigitNotZero())));
                verificationToken.setToken(token);

                ArgumentCaptor<VerificationToken> verificationTokenArgumentCaptor = ArgumentCaptor
                                .forClass(VerificationToken.class);
                ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

                given(verificationTokenRepository.findByToken(tokenString)).willReturn(Optional.of(verificationToken));

                // when
                tokenService.validateEmail(tokenString);

                // then
                BDDMockito.then(verificationTokenRepository).should(times(1))
                                .delete(verificationTokenArgumentCaptor.capture());
                BDDMockito.then(userRepository).should(times(1)).save(userArgumentCaptor.capture());

                then(verificationTokenArgumentCaptor.getValue()).isEqualTo(verificationToken);
                then(userArgumentCaptor.getValue().getIsVerified()).isEqualTo(true);

        }

        @Test
        void it_should_throw_exception_when_no_token_on_validate_email() {

                // given
                String tokenString = faker.random().hex();

                given(verificationTokenRepository.findByToken(tokenString)).willReturn(Optional.empty());

                // when, then
                assertThatThrownBy(() -> tokenService.validateEmail(tokenString))
                                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Null verification token");
        }

        @Test
        void it_should_throw_exception_when_token_expired_on_validate_email() {

                // given
                String tokenString = faker.random().hex();
                VerificationToken verificationToken = new VerificationToken();
                Token token = new Token();
                token.setToken(tokenString);
                token.setUser(user);
                token.setExpiresAt(Instant.now().minus(Duration.ofHours(faker.number().randomDigitNotZero())));
                verificationToken.setToken(token);

                given(verificationTokenRepository.findByToken(tokenString)).willReturn(Optional.of(verificationToken));

                // when, then

                assertThatThrownBy(() -> tokenService.validateEmail(tokenString))
                                .isInstanceOf(InvalidArgumentException.class).hasMessage("Token is expired");
        }

        @Test
        void it_should_validate_forgot_password_confirm() {

                // given
                String tokenString = faker.random().hex();
                PasswordForgotToken passwordForgotToken = new PasswordForgotToken();
                Token token = new Token();
                token.setToken(tokenString);
                token.setUser(user);
                token.setExpiresAt(Instant.now().plus(Duration.ofHours(faker.number().randomDigitNotZero())));
                passwordForgotToken.setToken(token);

                given(passwordForgotTokenRepository.findByTokenToken(tokenString))
                                .willReturn(Optional.of(passwordForgotToken));

                // when
                tokenService.validateForgotPasswordConfirm(tokenString);

                // then
                BDDMockito.then(passwordForgotTokenRepository).should(times(1)).findByTokenToken(tokenString);
        }

        @Test
        void it_should_throw_exception_when_no_token_on_validate_forgot_password_confirm() {

                // given
                String tokenString = faker.random().hex();

                given(passwordForgotTokenRepository.findByTokenToken(tokenString)).willReturn(Optional.empty());

                // when, then
                assertThatThrownBy(() -> tokenService.validateForgotPasswordConfirm(tokenString))
                                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Token not found");
        }

        @Test
        void it_should_throw_exception_when_token_expired_on_validate_forgot_password_confirm() {

                // given
                String tokenString = faker.random().hex();
                PasswordForgotToken passwordForgotToken = new PasswordForgotToken();
                Token token = new Token();
                token.setToken(tokenString);
                token.setUser(user);
                token.setExpiresAt(Instant.now().minus(Duration.ofHours(faker.number().randomDigitNotZero())));
                passwordForgotToken.setToken(token);

                given(passwordForgotTokenRepository.findByTokenToken(tokenString))
                                .willReturn(Optional.of(passwordForgotToken));

                // when, then
                assertThatThrownBy(() -> tokenService.validateForgotPasswordConfirm(tokenString))
                                .isInstanceOf(InvalidArgumentException.class).hasMessage("Token is expired");
        }

        @Test
        void it_should_validate_forgot_password() {

                // given
                String tokenString = faker.random().hex();
                String newPassword = faker.internet().password();

                PasswordForgotValidateRequest passwordForgotValidateRequest = new PasswordForgotValidateRequest();
                passwordForgotValidateRequest.setToken(tokenString);
                passwordForgotValidateRequest.setPassword(newPassword);
                passwordForgotValidateRequest.setPasswordConfirmation(newPassword);

                Token token = new Token();
                token.setToken(tokenString);
                token.setUser(user);
                token.setExpiresAt(Instant.now().plus(Duration.ofHours(faker.number().randomDigitNotZero())));

                PasswordForgotToken passwordForgotToken = new PasswordForgotToken();
                passwordForgotToken.setToken(token);

                ArgumentCaptor<PasswordForgotToken> passwordForgotTokenArgumentCaptor = ArgumentCaptor
                                .forClass(PasswordForgotToken.class);
                ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

                given(passwordForgotTokenRepository.findByTokenToken(tokenString))
                                .willReturn(Optional.of(passwordForgotToken));
                given(passwordEncoder.matches(passwordForgotValidateRequest.getPassword(), user.getPassword()))
                                .willReturn(false);
                given(passwordEncoder.encode(any())).willReturn(passwordForgotValidateRequest.getPassword());
                given(userRepository.save(user)).willReturn(user);

                // when
                tokenService.validateForgotPassword(passwordForgotValidateRequest);

                // then
                BDDMockito.then(passwordForgotTokenRepository).should(times(1))
                                .delete(passwordForgotTokenArgumentCaptor.capture());
                BDDMockito.then(userRepository).should(times(1)).save(userArgumentCaptor.capture());

                then(passwordForgotTokenArgumentCaptor.getValue()).isEqualTo(passwordForgotToken);
                then(userArgumentCaptor.getValue().getPassword()).isEqualTo(newPassword);

        }

        @Test
        void it_should_throw_exception_when_no_token_on_validate_forgot_password() {

                // given
                String tokenString = faker.random().hex();
                String newPassword = faker.internet().password();

                PasswordForgotValidateRequest passwordForgotValidateRequest = new PasswordForgotValidateRequest();
                passwordForgotValidateRequest.setToken(tokenString);
                passwordForgotValidateRequest.setPassword(newPassword);
                passwordForgotValidateRequest.setPasswordConfirmation(newPassword);

                given(passwordForgotTokenRepository.findByTokenToken(tokenString)).willReturn(Optional.empty());

                // when, then
                assertThatThrownBy(() -> tokenService.validateForgotPassword(passwordForgotValidateRequest))
                                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Token not found");
        }

        @Test
        void it_should_throw_exception_when_token_expired_on_validate_forgot_password() {

                // given
                String tokenString = faker.random().hex();
                String newPassword = faker.internet().password();

                PasswordForgotValidateRequest passwordForgotValidateRequest = new PasswordForgotValidateRequest();
                passwordForgotValidateRequest.setToken(tokenString);
                passwordForgotValidateRequest.setPassword(newPassword);
                passwordForgotValidateRequest.setPasswordConfirmation(newPassword);

                Token token = new Token();
                token.setToken(tokenString);
                token.setUser(user);
                token.setExpiresAt(Instant.now().minus(Duration.ofHours(faker.number().randomDigitNotZero())));

                PasswordForgotToken passwordForgotToken = new PasswordForgotToken();
                passwordForgotToken.setToken(token);

                given(passwordForgotTokenRepository.findByTokenToken(tokenString))
                                .willReturn(Optional.of(passwordForgotToken));

                // when, then
                assertThatThrownBy(() -> tokenService.validateForgotPassword(passwordForgotValidateRequest))
                                .isInstanceOf(InvalidArgumentException.class).hasMessage("Token is expired");
        }

}