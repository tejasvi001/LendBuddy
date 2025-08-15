package com.example.LendBuddy.services;

import com.example.LendBuddy.repositories.LoanRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    private final ModelMapper modelMapper;
    private final LoanRepository loanRepository;

    public LoanService(ModelMapper modelMapper, LoanRepository loanRepository) {
        this.modelMapper = modelMapper;
        this.loanRepository = loanRepository;
    }
}
