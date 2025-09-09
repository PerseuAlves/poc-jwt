package com.github.PerseuAlves.jwtexample.service;

/**
 * Exception thrown when an invalid email format is provided
 */
public class InvalidEmailException extends RuntimeException {
    
    public InvalidEmailException(String message) {
        super(message);
    }
    
    public InvalidEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}