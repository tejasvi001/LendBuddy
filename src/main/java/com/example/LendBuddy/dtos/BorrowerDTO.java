package com.example.LendBuddy.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowerDTO {
     @NotBlank(message = "phone number is required")
     @Pattern(regexp = "^[0-9]{10}$", message = "User's phone number must be 10 digits.")
     private String borrowerPhone;
     @NotBlank(message = "name is required")
     private String borrowerName;
}
