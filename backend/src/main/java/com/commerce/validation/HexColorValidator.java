package com.commerce.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HexColorValidator implements ConstraintValidator<HexColor, String> {

    private Pattern pattern;
    private Matcher matcher;

    private static final String HEX_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

    public HexColorValidator() {
        pattern = Pattern.compile(HEX_PATTERN);
    }

    @Override
    public boolean isValid(String hexColorCode, ConstraintValidatorContext context) {
        matcher = pattern.matcher(hexColorCode);
        return matcher.matches();
    }

}
