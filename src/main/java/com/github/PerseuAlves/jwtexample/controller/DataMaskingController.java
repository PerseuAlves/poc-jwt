package com.github.PerseuAlves.jwtexample.controller;

import com.github.PerseuAlves.jwtexample.service.DataMaskingService;
import com.github.PerseuAlves.jwtexample.service.MaskedContactData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to demonstrate data masking functionality
 */
@RestController
@RequestMapping("/api/masking")
public class DataMaskingController {

    @Autowired
    private DataMaskingService dataMaskingService;

    @PostMapping("/phone")
    public ResponseEntity<String> maskPhoneNumber(@RequestBody PhoneRequest request) {
        try {
            String masked = dataMaskingService.maskPhoneNumber(request.getPhoneNumber());
            return ResponseEntity.ok(masked);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/email")
    public ResponseEntity<String> maskEmail(@RequestBody EmailRequest request) {
        try {
            String masked = dataMaskingService.maskEmail(request.getEmail());
            return ResponseEntity.ok(masked);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/contact")
    public ResponseEntity<MaskedContactData> maskContactData(@RequestBody ContactRequest request) {
        try {
            MaskedContactData masked = dataMaskingService.maskContactData(
                request.getPhoneNumber(), 
                request.getEmail()
            );
            return ResponseEntity.ok(masked);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public static class PhoneRequest {
        private String phoneNumber;
        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    }

    public static class EmailRequest {
        private String email;
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    public static class ContactRequest {
        private String phoneNumber;
        private String email;
        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}