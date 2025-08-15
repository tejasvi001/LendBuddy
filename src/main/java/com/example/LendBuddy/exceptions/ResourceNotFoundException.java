package com.example.LendBuddy.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    ResourceNotFoundException(String message){
        super(message);
    }
}
