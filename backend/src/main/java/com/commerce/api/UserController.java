package com.commerce.api;

import javax.validation.Valid;

import com.commerce.model.request.user.PasswordResetRequest;
import com.commerce.model.request.user.UpdateUserAddressRequest;
import com.commerce.model.request.user.UpdateUserRequest;
import com.commerce.model.response.user.UserResponse;
import com.commerce.service.user.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/account")
@AllArgsConstructor
public class UserController extends ApiController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getUser() {

        UserResponse userResponse = userService.getCurrentUser();
        return new ResponseEntity<>(userResponse, HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {

        UserResponse userResponse = userService.updateUser(updateUserRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);

    }

    @PutMapping(path = "/address")
    public ResponseEntity<UserResponse> updateUserAddress(
            @RequestBody @Valid UpdateUserAddressRequest updateUserAddressRequest) {

        UserResponse userResponse = userService.updateUserAddress(updateUserAddressRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);

    }

    @PostMapping(path = "password/reset")
    public ResponseEntity<HttpStatus> passwordReset(@RequestBody @Valid PasswordResetRequest passwordResetRequest) {

        userService.resetPassword(passwordResetRequest);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(path = "/status")
    public ResponseEntity<Boolean> getVerificationStatus() {

        Boolean status = userService.getVerificationStatus();
        return new ResponseEntity<>(status, HttpStatus.OK);

    }

}
