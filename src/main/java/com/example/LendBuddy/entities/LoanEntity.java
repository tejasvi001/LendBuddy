package com.example.LendBuddy.entities;


import com.example.LendBuddy.utils.AmountType;
import com.example.LendBuddy.utils.InstallmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loans")
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long loanID;
    private String borrowerPhone;
    private String userPhone;
    private Double loanAmount;
    private Double interestRate;
    private Integer loanDuration;
    private Double totalAmount;
    private Double pendingAmount;
    private Double paidAmount;
    private Date issueDate;
    private Date closingDate;
    private Boolean loanClosedStatus;
    private String loanRemarks;
    private InstallmentType installmentType;
    private Double installmentAmount;
    private AmountType amountType;
    private Double fixedAmount;
}
