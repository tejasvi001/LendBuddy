package com.example.LendBuddy.services.implementations;

import com.example.LendBuddy.dtos.LoanDTO;
import com.example.LendBuddy.entities.LoanEntity;
import com.example.LendBuddy.entities.UserEntity;
import com.example.LendBuddy.exceptions.ResourceNotFoundException;
import com.example.LendBuddy.repositories.LoanRepository;
import com.example.LendBuddy.security.services.JWTService;
import com.example.LendBuddy.services.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoanServiceImpl implements LoanService {

    private final ModelMapper modelMapper;
    private final LoanRepository loanRepository;


    public LoanServiceImpl(ModelMapper modelMapper, LoanRepository loanRepository) {
        this.modelMapper = modelMapper;
        this.loanRepository = loanRepository;

    }

    @Override
    public List<LoanDTO> getAllLoans() {
        UserEntity user = getAuthenticatedUser();
        log.info("Fetching all loans for userId={}", user.getId());

        return loanRepository.findAllByIssuerID(user.getId())
                .stream()
                .map(loan -> modelMapper.map(loan, LoanDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public LoanDTO getLoanById(Integer loanId) {
        log.info("Fetching loan by loanId={}", loanId);

        LoanEntity loan = loanRepository.findById(Long.valueOf(loanId))
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id " + loanId));

        return modelMapper.map(loan, LoanDTO.class);
    }

    @Override
    @Transactional
    public LoanDTO createLoan(LoanDTO loanDTO) {
        UserEntity user = getAuthenticatedUser();
        log.info("Creating loan for userId={}, loanData={}", user.getId(), loanDTO);

        LoanEntity loanToCreate = modelMapper.map(loanDTO, LoanEntity.class);
        loanToCreate.setIssuerID(user.getId());

        LoanEntity savedLoan = loanRepository.save(loanToCreate);

        log.info("Loan created successfully with loanId={}", savedLoan.getLoanID());
        return modelMapper.map(savedLoan, LoanDTO.class);
    }

    /**
     * Utility method to extract authenticated user from SecurityContext
     */
    private UserEntity getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof UserEntity)) {
            throw new IllegalStateException("Authentication principal is not a valid UserEntity");
        }
        return (UserEntity) principal;
    }
}
