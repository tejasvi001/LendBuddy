package com.example.LendBuddy.repositories;

import com.example.LendBuddy.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity,Long> {
    List<LoanEntity> findAllByIssuerID(Long issuerId);
}
