package com.github.PerseuAlves.jwtexample.service;

/**
 * Service for masking sensitive data like phone numbers and emails
 */
public interface DataMaskingService {

    /**
     * Masks a Brazilian phone number showing DD + first digit + last 3 digits
     * 
     * @param phoneNumber Brazilian phone number in format DD + number
     * @return masked phone number
     * @throws InvalidPhoneNumberException if phone number format is invalid
     */
    String maskPhoneNumber(String phoneNumber);

    /**
     * Masks an email showing first 3 chars + last 3 chars before @ + domain
     * 
     * @param email email address to mask
     * @return masked email
     * @throws InvalidEmailException if email format is invalid
     */
    String maskEmail(String email);

    /**
     * Masks both phone number and email
     * 
     * @param phoneNumber Brazilian phone number
     * @param email email address
     * @return object containing both masked values
     */
    MaskedContactData maskContactData(String phoneNumber, String email);
}