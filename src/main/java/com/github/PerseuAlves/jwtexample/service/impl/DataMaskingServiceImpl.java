package com.github.PerseuAlves.jwtexample.service.impl;

import com.github.PerseuAlves.jwtexample.service.DataMaskingService;
import com.github.PerseuAlves.jwtexample.service.InvalidEmailException;
import com.github.PerseuAlves.jwtexample.service.InvalidPhoneNumberException;
import com.github.PerseuAlves.jwtexample.service.MaskedContactData;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Implementation of DataMaskingService for masking phone numbers and emails
 */
@Service
public class DataMaskingServiceImpl implements DataMaskingService {

    // Pattern for Brazilian phone numbers: DD (2 digits) + number (8 or 9 digits)
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{2}\\d{8,9}$");
    
    // Pattern for basic email validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");

    @Override
    public String maskPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new InvalidPhoneNumberException("Phone number cannot be null or empty");
        }

        String cleanPhone = phoneNumber.trim().replaceAll("[^\\d]", "");
        
        if (!PHONE_PATTERN.matcher(cleanPhone).matches()) {
            throw new InvalidPhoneNumberException("Invalid Brazilian phone number format. Expected: DD + 8-9 digits");
        }

        if (cleanPhone.length() < 10) {
            throw new InvalidPhoneNumberException("Phone number too short. Must have at least 10 digits (DD + 8 digits)");
        }

        // Extract DD (area code)
        String areaCode = cleanPhone.substring(0, 2);
        
        // Extract first digit after area code
        String firstDigit = cleanPhone.substring(2, 3);
        
        // Extract last 3 digits
        String lastThreeDigits = cleanPhone.substring(cleanPhone.length() - 3);
        
        // Calculate number of digits to mask
        // Total digits - DD(2) - first digit(1) - last 3 digits(3) = digits to mask
        int digitsToMask = cleanPhone.length() - 2 - 1 - 3;
        
        // Build masked phone number
        StringBuilder masked = new StringBuilder();
        masked.append(areaCode);
        masked.append(firstDigit);
        for (int i = 0; i < digitsToMask; i++) {
            masked.append("*");
        }
        masked.append(lastThreeDigits);
        
        return masked.toString();
    }

    @Override
    public String maskEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidEmailException("Email cannot be null or empty");
        }

        String cleanEmail = email.trim();
        
        if (!EMAIL_PATTERN.matcher(cleanEmail).matches()) {
            throw new InvalidEmailException("Invalid email format");
        }

        int atIndex = cleanEmail.indexOf('@');
        if (atIndex <= 0) {
            throw new InvalidEmailException("Invalid email format: missing or invalid @ symbol");
        }

        String localPart = cleanEmail.substring(0, atIndex);
        String domainPart = cleanEmail.substring(atIndex);

        if (localPart.length() < 6) {
            throw new InvalidEmailException("Email local part too short. Must have at least 6 characters to mask properly");
        }

        // Get first 3 characters
        String firstThree = localPart.substring(0, 3);
        
        // Get last 3 characters
        String lastThree = localPart.substring(localPart.length() - 3);
        
        // Calculate characters to mask
        int charsToMask = localPart.length() - 6; // total - (first 3 + last 3)
        
        // Build masked email
        StringBuilder masked = new StringBuilder();
        masked.append(firstThree);
        for (int i = 0; i < charsToMask; i++) {
            masked.append("*");
        }
        masked.append(lastThree);
        masked.append(domainPart);
        
        return masked.toString();
    }

    @Override
    public MaskedContactData maskContactData(String phoneNumber, String email) {
        String maskedPhone = maskPhoneNumber(phoneNumber);
        String maskedEmail = maskEmail(email);
        return new MaskedContactData(maskedPhone, maskedEmail);
    }
}