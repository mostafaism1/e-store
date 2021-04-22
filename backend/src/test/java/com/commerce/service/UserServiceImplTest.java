package com.commerce.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

import java.util.Optional;

import com.commerce.dao.UserRepository;
import com.commerce.error.exception.InvalidArgumentException;
import com.commerce.model.common.Address;
import com.commerce.model.dto.AddressDTO;
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

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Address address;
    private Boolean isVerified;
    private User validUser;
    private UserResponse validUserResponse;

    @BeforeEach
    public void setUp() {

        faker = new Faker();

        email = faker.internet().emailAddress();
        password = faker.internet().password(6, 52);
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        address = new Address();
        address.setCountry(faker.address().country());
        address.setState(faker.address().state());
        address.setCity(faker.address().city());
        address.setAddress(faker.address().streetAddress());
        address.setZip(faker.address().zipCode());
        address.setPhone(faker.number().digits(11));
        isVerified = true;

        validUser = new User();
        validUser.setEmail(email);
        validUser.setFirstName(firstName);
        validUser.setLastName(lastName);
        validUser.setAddress(address);
        validUser.setIsVerified(isVerified);

        validUserResponse = new UserResponse();
        validUserResponse.setEmail(email);
        validUserResponse.setFirstName(firstName);
        validUserResponse.setLastName(lastName);
        validUserResponse.setAddress(new AddressDTO(address.getCountry(), address.getState(), address.getCity(),
                address.getAddress(), address.getZip(), address.getPhone()));
        validUserResponse.setIsVerified(isVerified);

    }

    @Test
    void Should_Register_Given_NonConflictingEmailAddress() {

        // given
        RegisterRequest registerUserRequest = new RegisterRequest();
        registerUserRequest.setEmail(email);
        registerUserRequest.setPassword(password);
        registerUserRequest.setPasswordConfirmation(password);

        UserResponse expected = new UserResponse();
        expected.setEmail(email);
        expected.setIsVerified(false);

        given(userRepository.existsByEmail(email)).willReturn(false);

        given(userRepository.save(any(User.class))).willAnswer(invocation -> {
            User user = (User) invocation.getArguments()[0];
            ReflectionTestUtils.setField(user, "id", 123L);
            return user;
        });

        // when
        UserResponse actual = userService.register(registerUserRequest);

        // then
        BDDMockito.then(userRepository).should(times(1)).save(any(User.class));
        then(actual).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void ShouldNot_Register_Given_ConflictingEmailAddress() {

        // given
        RegisterRequest registerUserRequest = new RegisterRequest();
        registerUserRequest.setEmail(email);
        registerUserRequest.setPassword(password);
        registerUserRequest.setPasswordConfirmation(password);

        given(userRepository.existsByEmail(email)).willReturn(true);

        // when, then
        assertThatThrownBy(() -> userService.register(registerUserRequest)).isInstanceOf(InvalidArgumentException.class)
                .hasMessage("An account already exists with this email");

    }


    @Test
    void Should_FindUser_Given_RegisteredEmail() {

        // given
        given(userRepository.findByEmail(email)).willReturn(Optional.of(validUser));

        // when
        UserResponse actual = userService.findByEmail(email);

        // then
        BDDMockito.then(userRepository).should(times(1)).findByEmail(email);
        then(actual).usingRecursiveComparison().isEqualTo(validUserResponse);

    }

    @Test
    void ShouldNot_FindUser_Given_NonRegisteredEmail() {

        // given
        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        // when
        UserResponse actual = userService.findByEmail(email);

        // then
        BDDMockito.then(userRepository).should(times(1)).findByEmail(email);
        then(actual).usingRecursiveComparison().isEqualTo(null);

    }

}
