package com.example.LendBuddy.services.implementations;

import com.example.LendBuddy.repositories.BorrowerRepository;
import com.example.LendBuddy.services.BorrowerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BorrowerServiceImpl implements BorrowerService {
    private  final BorrowerRepository borrowerRepository;
    private  final ModelMapper modelMapper;

    public BorrowerServiceImpl(BorrowerRepository borrowerRepository, ModelMapper modelMapper) {
        this.borrowerRepository = borrowerRepository;
        this.modelMapper = modelMapper;
    }
}
