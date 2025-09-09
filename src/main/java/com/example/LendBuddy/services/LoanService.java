package com.example.LendBuddy.services;

import com.example.LendBuddy.dtos.LoanDTO;
import com.example.LendBuddy.repositories.LoanRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


public interface LoanService {

    List<LoanDTO> getAllLoans();

    LoanDTO getLoanById(Integer loanId);

    LoanDTO createLoan(LoanDTO loanDTO);

    void deleteLoanById(Long loanId);
}
