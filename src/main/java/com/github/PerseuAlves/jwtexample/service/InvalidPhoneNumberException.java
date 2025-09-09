package com.github.PerseuAlves.jwtexample.service;

/**
 * Exception thrown when an invalid phone number format is provided
 */
public class InvalidPhoneNumberException extends RuntimeException {
    
    public InvalidPhoneNumberException(String message) {
        super(message);
    }
    
    public InvalidPhoneNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}