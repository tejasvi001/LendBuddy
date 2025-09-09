package com.example.LendBuddy.repositories;

import com.example.LendBuddy.entities.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity,Long> {
    List<CollectionEntity> findAllByLoanID(Long loanId);
}
