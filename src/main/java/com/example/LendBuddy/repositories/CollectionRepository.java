package com.example.LendBuddy.repositories;

import com.example.LendBuddy.entities.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity,Long> {
}
