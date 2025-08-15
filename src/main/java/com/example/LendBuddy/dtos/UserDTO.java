package com.example.LendBuddy.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    @NotBlank(message = "phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "User's phone number must be 10 digits.")
    private String userPhone;
    @NotBlank(message = "name is required")
    private String userName;
    @Email(message = "email should be valid")
    @NotBlank (message = "email is a mandatory field")
    private String userEmail;
    @NotBlank(message = "verificationcode is required")
    @Pattern(regexp = "^[0-9]{4}$", message = "User's phone number must be 4 digits.")
    private String userVerificationCode;
}
