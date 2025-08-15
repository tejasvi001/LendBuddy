package com.example.LendBuddy.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.sql.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionDTO {
    private Long collectionID;
    @NotBlank(message="Collection Date is a required field")
    @PastOrPresent(message = "Issue date must be in the past or present.")
    private Date collectionDate;
    @NotBlank(message="Collection Amount is a required field")
    private Double collectionAmount;
    @NotBlank(message = "Loan ID is a required field")
    private Long loanID;
}
