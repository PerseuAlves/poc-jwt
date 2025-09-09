package com.github.PerseuAlves.jwtexample.service;

/**
 * Data class to hold masked contact information
 */
public class MaskedContactData {
    private final String maskedPhoneNumber;
    private final String maskedEmail;

    public MaskedContactData(String maskedPhoneNumber, String maskedEmail) {
        this.maskedPhoneNumber = maskedPhoneNumber;
        this.maskedEmail = maskedEmail;
    }

    public String getMaskedPhoneNumber() {
        return maskedPhoneNumber;
    }

    public String getMaskedEmail() {
        return maskedEmail;
    }

    @Override
    public String toString() {
        return "MaskedContactData{" +
                "maskedPhoneNumber='" + maskedPhoneNumber + '\'' +
                ", maskedEmail='" + maskedEmail + '\'' +
                '}';
    }
}