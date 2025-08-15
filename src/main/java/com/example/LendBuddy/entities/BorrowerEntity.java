package com.example.LendBuddy.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "borrowers")
public class BorrowerEntity {
    @Id
    private String borrowerPhone;
    private String borrowerName;
}
