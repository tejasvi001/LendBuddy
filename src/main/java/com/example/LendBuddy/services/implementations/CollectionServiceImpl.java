package com.example.LendBuddy.services.implementations;

import com.example.LendBuddy.dtos.CollectionDTO;
import com.example.LendBuddy.entities.CollectionEntity;
import com.example.LendBuddy.entities.LoanEntity;
import com.example.LendBuddy.entities.UserEntity;
import com.example.LendBuddy.exceptions.ResourceNotFoundException;
import com.example.LendBuddy.repositories.CollectionRepository;
import com.example.LendBuddy.repositories.LoanRepository;
import com.example.LendBuddy.services.CollectionService;
import lombok.extern.slf4j.Slf4j; // <-- Add this import
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j  // <-- Lombok will generate a 'log' instance
public class CollectionServiceImpl implements CollectionService {
    private final CollectionRepository collectionRepository;
    private final ModelMapper modelMapper;
    private final LoanRepository loanRepository;

    public CollectionServiceImpl(CollectionRepository collectionRepository, ModelMapper modelMapper, LoanRepository loanRepository) {
        this.collectionRepository = collectionRepository;
        this.modelMapper = modelMapper;
        this.loanRepository = loanRepository;
    }

    @Override
    public CollectionDTO createCollection(CollectionDTO collectionDTO) {
        log.info("Attempting to create collection for loanId={} with amount={}",
                collectionDTO.getLoanID(), collectionDTO.getCollectionAmount());

        CollectionEntity toSave = modelMapper.map(collectionDTO, CollectionEntity.class);

        if (!isIssuerOfLoan(toSave.getLoanID(), getAuthenticatedUser().getId())) {
            log.warn("Unauthorized attempt to create collection for loanId={} by userId={}",
                    toSave.getLoanID(), getAuthenticatedUser().getId());
            throw new AccessDeniedException(
                    "You are not authorized to create a collection for loanId=" + toSave.getLoanID()
            );
        }

        CollectionEntity savedCollection = collectionRepository.save(toSave);
        log.info("Collection created successfully with id={} for loanId={}",
                savedCollection.getCollectionID(), savedCollection.getLoanID());

        return modelMapper.map(savedCollection, CollectionDTO.class);
    }

    @Override
    public List<CollectionDTO> getCollectionsByLoanId(Long loanId) {
        log.debug("Fetching collections for loanId={}", loanId);

        if (!loanRepository.existsById(loanId)) {
            log.error("Loan not found with id={}", loanId);
            throw new ResourceNotFoundException("Loan not found with id " + loanId);
        }

        List<CollectionEntity> collections = collectionRepository.findAllByLoanID(loanId);
        log.info("Found {} collections for loanId={}", collections.size(), loanId);

        return collections.stream()
                .map(collection -> modelMapper.map(collection, CollectionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CollectionDTO getCollectionById(Long id) {
        log.debug("Fetching collection by id={}", id);

        CollectionEntity collection = collectionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Collection not found with id={}", id);
                    return new ResourceNotFoundException("Collection not found with id " + id);
                });

        log.info("Fetched collection with id={} for loanId={}", collection.getCollectionID(), collection.getLoanID());
        return modelMapper.map(collection, CollectionDTO.class);
    }

    @Override
    public List<CollectionDTO> getCollectionsByIssuerId(Long issuerId) {
        log.debug("Fetching collections for issuerId={}", issuerId);

        List<LoanEntity> loans = loanRepository.findAllByIssuerID(issuerId);
        List<Long> loanIds = loans.stream().map(LoanEntity::getLoanID).toList();

        List<CollectionEntity> collections = loanIds.stream()
                .flatMap(loanId -> collectionRepository.findAllByLoanID(loanId).stream())
                .toList();

        log.info("Found {} collections for issuerId={}", collections.size(), issuerId);

        return collections.stream()
                .map(collectionEntity -> modelMapper.map(collectionEntity, CollectionDTO.class))
                .collect(Collectors.toList());
    }

    private UserEntity getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof UserEntity)) {
            log.error("Authentication principal is not a valid UserEntity. Principal={}", principal);
            throw new IllegalStateException("Authentication principal is not a valid UserEntity");
        }

        UserEntity user = (UserEntity) principal;
        log.debug("Authenticated user: id={}, phone={}", user.getId(), user.getPhone());
        return user;
    }

    public boolean isIssuerOfLoan(Long loanId, Long userId) {
        boolean exists = loanRepository.existsByLoanIDAndIssuerID(loanId, userId);
        log.debug("Checking if userId={} is issuer of loanId={} -> {}", userId, loanId, exists);
        return exists;
    }
}
