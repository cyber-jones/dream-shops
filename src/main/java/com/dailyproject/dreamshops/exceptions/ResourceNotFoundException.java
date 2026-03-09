package com.dailyproject.dreamshops.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String categoryNotFound) {
        super("Resource not found: " + categoryNotFound);
    }
}
