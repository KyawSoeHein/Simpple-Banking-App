package org.gic.ui.inputReader;

import org.gic.model.Account;
import org.gic.singleton.AccountStorage;
import org.gic.singleton.TextScanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InputAccountNumberReaderTest {

    @BeforeEach
    void setUp() {
        AccountStorage.getAccountStorage().clear();
    }

    @Test
    void testAccountDoesNotExist() throws Exception {
        try (MockedStatic<TextScanner> mockedTextScanner = mockStatic(TextScanner.class)) {
            // Arrange
            Scanner scannerMock = mock(Scanner.class);
            when(scannerMock.nextLine()).thenReturn("11111");
            mockedTextScanner.when(TextScanner::getScanner).thenReturn(scannerMock);

            // Act & Assert
            assertThrows(IllegalArgumentException.class, InputAccountNumberReader::readAccountNumber);
        }
    }

    @Test
    void testAccountInputAsNull() throws Exception {
        try (MockedStatic<TextScanner> mockedTextScanner = mockStatic(TextScanner.class)) {
            // Arrange
            Scanner scannerMock = mock(Scanner.class);
            when(scannerMock.nextLine()).thenReturn(null);
            mockedTextScanner.when(TextScanner::getScanner).thenReturn(scannerMock);

            // Act & Assert
            assertThrows(IllegalArgumentException.class, InputAccountNumberReader::readAccountNumber);
        }
    }

    @Test
    void testAccountInputAsBlank() throws Exception {
        try (MockedStatic<TextScanner> mockedTextScanner = mockStatic(TextScanner.class)) {
            // Arrange
            Scanner scannerMock = mock(Scanner.class);
            when(scannerMock.nextLine()).thenReturn("   ");
            mockedTextScanner.when(TextScanner::getScanner).thenReturn(scannerMock);

            // Act & Assert
            assertThrows(IllegalArgumentException.class, InputAccountNumberReader::readAccountNumber);
        }
    }

    @Test
    void testAccountExist() throws Exception {
        try (MockedStatic<TextScanner> mockedTextScanner = mockStatic(TextScanner.class)) {
            // Arrange
            Account account = new Account("1111", new BigDecimal("0"), new TreeMap<>());
            AccountStorage.getAccountStorage().put(account.getAccountNumber(), account);
            Scanner scannerMock = mock(Scanner.class);
            when(scannerMock.nextLine()).thenReturn("1111");
            mockedTextScanner.when(TextScanner::getScanner).thenReturn(scannerMock);

            // Act & Assert
            assertDoesNotThrow(InputAccountNumberReader::readAccountNumber);
            assertEquals("1111", InputAccountNumberReader.readAccountNumber().getAccountNumber());
        }
    }

}