package com.example.LendBuddy.services;

import com.example.LendBuddy.repositories.CollectionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private  final ModelMapper modelMapper;

    public CollectionService(CollectionRepository collectionRepository, ModelMapper modelMapper) {
        this.collectionRepository = collectionRepository;
        this.modelMapper = modelMapper;
    }
}
