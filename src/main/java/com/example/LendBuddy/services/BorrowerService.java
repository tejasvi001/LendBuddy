package com.example.LendBuddy.services;

import com.example.LendBuddy.repositories.BorrowerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BorrowerService {
    private  final BorrowerRepository borrowerRepository;
    private  final ModelMapper modelMapper;

    public BorrowerService(BorrowerRepository borrowerRepository, ModelMapper modelMapper) {
        this.borrowerRepository = borrowerRepository;
        this.modelMapper = modelMapper;
    }
}
