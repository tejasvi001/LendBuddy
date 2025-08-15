package com.example.LendBuddy.repositories;

import com.example.LendBuddy.entities.BorrowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerRepository extends JpaRepository<BorrowerEntity,String> {
}
