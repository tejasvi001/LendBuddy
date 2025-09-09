package com.example.LendBuddy.services;

import com.example.LendBuddy.dtos.CollectionDTO;
import com.example.LendBuddy.repositories.CollectionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CollectionService {

    CollectionDTO createCollection(CollectionDTO collectionDTO);

    List<CollectionDTO> getCollectionsByLoanId(Long loanId);

    CollectionDTO getCollectionById(Long id);

    List<CollectionDTO> getCollectionsByIssuerId(Long id);
}
