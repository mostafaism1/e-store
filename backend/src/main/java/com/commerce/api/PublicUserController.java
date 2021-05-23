package com.commerce.api;

import javax.validation.Valid;

import com.commerce.model.entity.User;
import com.commerce.model.request.user.PasswordForgotConfirmRequest;
import com.commerce.model.request.user.PasswordForgotRequest;
import com.commerce.model.request.user.PasswordForgotValidateRequest;
import com.commerce.model.request.user.RegisterRequest;
import com.commerce.model.request.user.ValidateEmailRequest;
import com.commerce.model.response.user.UserResponse;
import com.commerce.service.token.TokenService;
import com.commerce.service.user.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/account")
@AllArgsConstructor
public class PublicUserController extends PublicApiController {

    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping(path = "/registration")
    public ResponseEntity<HttpStatus> registerUser(@RequestBody @Valid RegisterRequest registerRequest) {

        UserResponse userResponse = userService.register(registerRequest);
        createEmailConfirmToken(userResponse);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping(path = "/registration/validate")
    public ResponseEntity<HttpStatus> validateEmail(@RequestBody @Valid ValidateEmailRequest validateEmailRequest) {

        tokenService.validateEmail(validateEmailRequest.getToken());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping(path = "/password/forgot")
    public ResponseEntity<HttpStatus> forgotPasswordRequest(
            @RequestBody @Valid PasswordForgotRequest passwordForgotRequest) {

        tokenService.createPasswordResetToken(passwordForgotRequest.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping(path = "/password/forgot/validate")
    public ResponseEntity<HttpStatus> validateForgotPassword(
            @RequestBody @Valid PasswordForgotValidateRequest passwordForgotValidateRequest) {

        tokenService.validateForgotPassword(passwordForgotValidateRequest);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping(path = "/password/forgot/confirm")
    public ResponseEntity<HttpStatus> confirmForgotPassword(
            @RequestBody @Valid PasswordForgotConfirmRequest passwordForgotConfirmRequest) {

        tokenService.validateForgotPasswordConfirm(passwordForgotConfirmRequest.getToken());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    private void createEmailConfirmToken(UserResponse userResponse) {

        User user = new User();
        user.setEmail(userResponse.getEmail());
        tokenService.createEmailConfirmToken(user);

    }

}
