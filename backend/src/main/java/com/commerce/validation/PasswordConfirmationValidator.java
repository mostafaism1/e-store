package com.commerce.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmationValidator implements ConstraintValidator<PasswordConfirmation, PasswordConfirmer> {

    @Override
    public boolean isValid(PasswordConfirmer registerUserRequest, ConstraintValidatorContext context) {
        return registerUserRequest.getPassword().equals(registerUserRequest.getPasswordConfirmation());
    }

}
