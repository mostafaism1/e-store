package com.commerce.service.token;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.commerce.dao.VerificationTokenRepository;
import com.commerce.model.entity.User;
import com.commerce.model.entity.VerificationToken;
import com.commerce.model.event.OnRegistrationCompleteEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    void it_should_create_email_confirm_token() {

        // given
        ArgumentCaptor<VerificationToken> verificationTokenArgumentCaptor = ArgumentCaptor
                .forClass(VerificationToken.class);
        ArgumentCaptor<OnRegistrationCompleteEvent> onRegistrationCompleteEventArgumentCaptor = ArgumentCaptor
                .forClass(OnRegistrationCompleteEvent.class);

        given(verificationTokenRepository.save(any(VerificationToken.class))).willReturn(new VerificationToken());

        // when
        tokenService.createEmailConfirmToken(user);

        // then
        verify(verificationTokenRepository).save(verificationTokenArgumentCaptor.capture());
        verify(eventPublisher).publishEvent(onRegistrationCompleteEventArgumentCaptor.capture());

        then(user).isEqualTo(onRegistrationCompleteEventArgumentCaptor.getValue().getUser());
        then(verificationTokenArgumentCaptor.getValue().getToken().getToken())
                .isEqualTo(onRegistrationCompleteEventArgumentCaptor.getValue().getToken());

    }
}