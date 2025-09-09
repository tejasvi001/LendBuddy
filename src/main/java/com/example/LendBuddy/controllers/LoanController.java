package com.example.LendBuddy.controllers;

import com.example.LendBuddy.dtos.LoanDTO;
import com.example.LendBuddy.services.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/loan")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        List<LoanDTO> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans); // 200 OK
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanDTO> getLoanByID(@PathVariable Integer loanId) {
        LoanDTO loan = loanService.getLoanById(loanId);
        return ResponseEntity.ok(loan); // 200 OK
    }

    @PostMapping
    public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO loanDTO) {
        LoanDTO createdLoan = loanService.createLoan(loanDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan); // 201 Created
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long loanId){
        loanService.deleteLoanById(loanId);
        return ResponseEntity.ok(null);
    }
}
