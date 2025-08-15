package com.example.LendBuddy.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = PasswordValidator.class
)
public @interface PasswordValidation {
    String message() default "The password should contain at least 1 digit, 1 lowercase, 1 uppercase, 1 special and minimum length of 10";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
