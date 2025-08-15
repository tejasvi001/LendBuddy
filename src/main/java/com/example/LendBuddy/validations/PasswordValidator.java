package com.example.LendBuddy.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValidation,String> {




    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null || password.isEmpty()) {
            return false; // Null or empty passwords are not valid
        }

        // Password rules: at least 1 digit, 1 lowercase, 1 uppercase, 1 special character, minimum length of 10
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{10,}$";

        return password.matches(passwordRegex);
    }
}
