package com.commerce.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.commerce.dao.UserRepository;
import com.commerce.error.exception.InvalidArgumentException;
import com.commerce.model.entity.User;
import com.commerce.model.request.user.RegisterRequest;
import com.commerce.model.response.user.UserResponse;
import com.github.javafaker.Faker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @Test
    void Should_Register_Given_NonConflictingEmailAddress() {

        // given
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(6, 52);
        RegisterRequest registerUserRequest = new RegisterRequest();
        registerUserRequest.setEmail(email);
        registerUserRequest.setPassword(password);
        registerUserRequest.setPasswordConfirmation(password);

        UserResponse userExpected = new UserResponse();
        userExpected.setEmail(email);
        userExpected.setAddress(null);
        userExpected.setFirstName(null);
        userExpected.setLastName(null);
        userExpected.setIsVerified(false);

        given(userRepository.existsByEmail(email)).willReturn(false);

        given(userRepository.save(any(User.class))).willAnswer(invocation -> {
            User user = (User) invocation.getArguments()[0];
            ReflectionTestUtils.setField(user, "id", 123L);
            return user;
        });

        // when
        UserResponse userActual = userService.register(registerUserRequest);

        // then        
        BDDMockito.then(userRepository).should(times(1)).save(any(User.class));
        then(userActual).usingRecursiveComparison().isEqualTo(userExpected);

    }

    @Test
    void ShouldNot_Register_Given_ConflictingEmailAddress() {

        // given
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(6, 52);
        RegisterRequest registerUserRequest = new RegisterRequest();
        registerUserRequest.setEmail(email);
        registerUserRequest.setPassword(password);
        registerUserRequest.setPasswordConfirmation(password);

        given(userRepository.existsByEmail(email)).willReturn(true);

        // when, then
        assertThatThrownBy(() -> userService.register(registerUserRequest)).isInstanceOf(InvalidArgumentException.class)
                .hasMessage("An account already exists with this email");

    }

}
