package com.example.LendBuddy.controllers;

import com.example.LendBuddy.dtos.CollectionDTO;
import com.example.LendBuddy.services.CollectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/collection")
public class CollectionController {
    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @PostMapping
    public ResponseEntity<CollectionDTO> createCollection(@RequestBody CollectionDTO collectionDTO){
        CollectionDTO collection=collectionService.createCollection(collectionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(collection);
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<CollectionDTO>> getCollectionByLoanId(@PathVariable Long loanId){
        List<CollectionDTO> collectionDTOS=collectionService.getCollectionsByLoanId(loanId);
        return ResponseEntity.ok(collectionDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionDTO> getCollectionByID(@PathVariable Long id){
        CollectionDTO collectionDTO=collectionService.getCollectionById(id);
        return ResponseEntity.ok(collectionDTO);
    }

    @GetMapping("/issuer/{id}")
    public ResponseEntity<List<CollectionDTO>> getCollectionByIssuerId(@PathVariable Long id){
        List<CollectionDTO> collectionDTOS=collectionService.getCollectionsByIssuerId(id);
        return ResponseEntity.ok(collectionDTOS);
    }
}
