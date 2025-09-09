package com.github.PerseuAlves.jwtexample.service.impl;

import com.github.PerseuAlves.jwtexample.service.DataMaskingService;
import com.github.PerseuAlves.jwtexample.service.InvalidEmailException;
import com.github.PerseuAlves.jwtexample.service.InvalidPhoneNumberException;
import com.github.PerseuAlves.jwtexample.service.MaskedContactData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataMaskingServiceImplTest {

    private DataMaskingService dataMaskingService;

    @BeforeEach
    void setUp() {
        dataMaskingService = new DataMaskingServiceImpl();
    }

    @Test
    void testMaskPhoneNumber_ValidPhone10Digits() {
        // Brazilian phone: DD + 8 digits, show DD + first digit + last 3, mask the middle
        String result = dataMaskingService.maskPhoneNumber("1198765953");
        assertEquals("119****953", result);
    }

    @Test
    void testMaskPhoneNumber_ValidPhone11Digits() {
        String result = dataMaskingService.maskPhoneNumber("11987654321");
        assertEquals("119*****321", result);
    }

    @Test
    void testMaskPhoneNumber_ValidPhoneWithFormatting() {
        String result = dataMaskingService.maskPhoneNumber("(11) 98765-4321");
        assertEquals("119*****321", result);
    }

    @Test
    void testMaskPhoneNumber_ValidPhoneWithSpaces() {
        String result = dataMaskingService.maskPhoneNumber("11 98765 4321");
        assertEquals("119*****321", result);
    }

    @Test
    void testMaskPhoneNumber_DifferentAreaCodes() {
        assertEquals("219*****321", dataMaskingService.maskPhoneNumber("21987654321"));
        assertEquals("859*****321", dataMaskingService.maskPhoneNumber("85987654321"));
    }

    @Test
    void testMaskPhoneNumber_NullPhone() {
        assertThrows(InvalidPhoneNumberException.class, () -> {
            dataMaskingService.maskPhoneNumber(null);
        });
    }

    @Test
    void testMaskPhoneNumber_EmptyPhone() {
        assertThrows(InvalidPhoneNumberException.class, () -> {
            dataMaskingService.maskPhoneNumber("");
        });
    }

    @Test
    void testMaskPhoneNumber_WhitespacePhone() {
        assertThrows(InvalidPhoneNumberException.class, () -> {
            dataMaskingService.maskPhoneNumber("   ");
        });
    }

    @Test
    void testMaskPhoneNumber_TooShort() {
        assertThrows(InvalidPhoneNumberException.class, () -> {
            dataMaskingService.maskPhoneNumber("119876543");
        });
    }

    @Test
    void testMaskPhoneNumber_TooLong() {
        assertThrows(InvalidPhoneNumberException.class, () -> {
            dataMaskingService.maskPhoneNumber("119876543210");
        });
    }

    @Test
    void testMaskPhoneNumber_InvalidCharacters() {
        assertThrows(InvalidPhoneNumberException.class, () -> {
            dataMaskingService.maskPhoneNumber("11abc654321");
        });
    }

    @Test
    void testMaskEmail_ValidEmail() {
        // Email masking: show first 3 + last 3 of local part + full domain
        String result = dataMaskingService.maskEmail("perseu.oliveira@gmail.com");
        assertEquals("per*********ira@gmail.com", result);
    }

    @Test
    void testMaskEmail_ValidEmailDifferentDomain() {
        String result = dataMaskingService.maskEmail("john.doe@example.org");
        assertEquals("joh**doe@example.org", result);
    }

    @Test
    void testMaskEmail_MinimumValidEmail() {
        String result = dataMaskingService.maskEmail("abcdef@test.com");
        assertEquals("abcdef@test.com", result);
    }

    @Test
    void testMaskEmail_LongerEmail() {
        String result = dataMaskingService.maskEmail("verylongemailname@company.com");
        assertEquals("ver***********ame@company.com", result);
    }

    @Test
    void testMaskEmail_NullEmail() {
        assertThrows(InvalidEmailException.class, () -> {
            dataMaskingService.maskEmail(null);
        });
    }

    @Test
    void testMaskEmail_EmptyEmail() {
        assertThrows(InvalidEmailException.class, () -> {
            dataMaskingService.maskEmail("");
        });
    }

    @Test
    void testMaskEmail_WhitespaceEmail() {
        assertThrows(InvalidEmailException.class, () -> {
            dataMaskingService.maskEmail("   ");
        });
    }

    @Test
    void testMaskEmail_NoAtSymbol() {
        assertThrows(InvalidEmailException.class, () -> {
            dataMaskingService.maskEmail("invalidemail.com");
        });
    }

    @Test
    void testMaskEmail_MultipleAtSymbols() {
        assertThrows(InvalidEmailException.class, () -> {
            dataMaskingService.maskEmail("invalid@email@domain.com");
        });
    }

    @Test
    void testMaskEmail_TooShortLocalPart() {
        assertThrows(InvalidEmailException.class, () -> {
            dataMaskingService.maskEmail("short@domain.com");
        });
    }

    @Test
    void testMaskEmail_EmptyLocalPart() {
        assertThrows(InvalidEmailException.class, () -> {
            dataMaskingService.maskEmail("@domain.com");
        });
    }

    @Test
    void testMaskEmail_EmptyDomain() {
        assertThrows(InvalidEmailException.class, () -> {
            dataMaskingService.maskEmail("user@");
        });
    }

    @Test
    void testMaskContactData_ValidInputs() {
        MaskedContactData result = dataMaskingService.maskContactData("11987654321", "perseu.oliveira@gmail.com");
        
        assertNotNull(result);
        assertEquals("119*****321", result.getMaskedPhoneNumber());
        assertEquals("per*********ira@gmail.com", result.getMaskedEmail());
    }

    @Test
    void testMaskContactData_InvalidPhone() {
        assertThrows(InvalidPhoneNumberException.class, () -> {
            dataMaskingService.maskContactData("invalid", "valid@email.com");
        });
    }

    @Test
    void testMaskContactData_InvalidEmail() {
        assertThrows(InvalidEmailException.class, () -> {
            dataMaskingService.maskContactData("11987654321", "invalid-email");
        });
    }

    @Test
    void testMaskedContactData_ToString() {
        MaskedContactData data = new MaskedContactData("119*****321", "per*********ira@gmail.com");
        String result = data.toString();
        
        assertTrue(result.contains("119*****321"));
        assertTrue(result.contains("per*********ira@gmail.com"));
        assertTrue(result.contains("MaskedContactData"));
    }
}