package com.example.LendBuddy.dtos;

import com.example.LendBuddy.utils.AmountType;
import com.example.LendBuddy.utils.InstallmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {

    private Long loanID;

    @NotBlank(message = "Borrower's phone number cannot be blank.")
    @Pattern(regexp = "^[0-9]{10}$", message = "Borrower's phone number must be 10 digits.")
    private String borrowerPhone;

    @NotBlank(message = "User's phone number cannot be blank.")
    @Pattern(regexp = "^[0-9]{10}$", message = "User's phone number must be 10 digits.")
    private String userPhone;

    @NotNull(message = "Loan amount cannot be null.")
    @Positive(message = "Loan amount must be a positive value.")
    private Double loanAmount;

    @DecimalMin(value = "0.0", inclusive = false, message = "Interest rate must be greater than 0.")
    @DecimalMax(value = "100.0", message = "Interest rate must not exceed 100%.")
    private Double interestRate;

    @NotNull(message = "Loan duration cannot be null.")
    @Positive(message = "Loan duration must be a positive value.")
    private Integer loanDuration;

    @NotNull(message = "Total amount cannot be null.")
    @Positive(message = "Total amount must be a positive value.")
    private Double totalAmount;

    @NotNull(message = "Pending amount cannot be null.")
    @PositiveOrZero(message = "Pending amount cannot be negative.")
    private Double pendingAmount;

    @NotNull(message = "Paid amount cannot be null.")
    @PositiveOrZero(message = "Paid amount cannot be negative.")
    private Double paidAmount;

    @NotNull(message = "Issue date cannot be null.")
    @PastOrPresent(message = "Issue date must be in the past or present.")
    private Date issueDate;

    @FutureOrPresent(message = "Closing date must be in the future or present.")
    private Date closingDate;

    private Boolean loanClosedStatus;

    @Size(max = 255, message = "Loan remarks cannot exceed 255 characters.")
    private String loanRemarks;

    @NotNull(message = "Installment type cannot be null.")
    private InstallmentType installmentType;

    @NotNull(message = "Installment amount cannot be null.")
    @Positive(message = "Installment amount must be a positive value.")
    private Double installmentAmount;

    @NotNull(message = "Amount type cannot be null.")
    private AmountType amountType;

    @Positive(message = "Fixed amount must be a positive value.")
    private Double fixedAmount;
}
