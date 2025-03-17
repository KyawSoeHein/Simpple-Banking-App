package org.gic.ui.inputReader;

import org.gic.enums.NavigationRoutes;
import org.gic.singleton.TextScanner;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InputMenuOptionReaderTest {
    @Test
    void testWithInValidInput(){
        try (MockedStatic<TextScanner> mockedTextScanner = mockStatic(TextScanner.class)) {
            // Arrange
            Scanner scannerMock = mock(Scanner.class);
            when(scannerMock.nextLine()).thenReturn("Y");
            mockedTextScanner.when(TextScanner::getScanner).thenReturn(scannerMock);

            // Act
            NavigationRoutes result = InputMenuOptionReader.showAndReceiveInput();

            // Assert
            assertEquals(NavigationRoutes.GO_TO_MENU_PAGE, result);
        }
    }

    @Test
    void testWithInValidInputBlank(){
        try (MockedStatic<TextScanner> mockedTextScanner = mockStatic(TextScanner.class)) {
            // Arrange
            Scanner scannerMock = mock(Scanner.class);
            when(scannerMock.nextLine()).thenReturn("    ");
            mockedTextScanner.when(TextScanner::getScanner).thenReturn(scannerMock);

            // Act
            NavigationRoutes result = InputMenuOptionReader.showAndReceiveInput();

            // Assert
            assertEquals(NavigationRoutes.GO_TO_MENU_PAGE, result);
        }
    }

    @Test
    void testWithInValidSmallLetter() {
        try (MockedStatic<TextScanner> mockedTextScanner = mockStatic(TextScanner.class)) {
            // Arrange
            Scanner scannerMock = mock(Scanner.class);
            when(scannerMock.nextLine()).thenReturn("y");
            mockedTextScanner.when(TextScanner::getScanner).thenReturn(scannerMock);

            // Act
            NavigationRoutes result = InputMenuOptionReader.showAndReceiveInput();

            // Assert
            assertEquals(NavigationRoutes.GO_TO_MENU_PAGE, result);
        }
    }

    @Test
    void testWithValidSmallLetter() {
        try (MockedStatic<TextScanner> mockedTextScanner = mockStatic(TextScanner.class)) {
            // Arrange
            Scanner scannerMock = mock(Scanner.class);
            when(scannerMock.nextLine()).thenReturn("t");
            mockedTextScanner.when(TextScanner::getScanner).thenReturn(scannerMock);

            // Act
            NavigationRoutes result = InputMenuOptionReader.showAndReceiveInput();

            // Assert
            assertEquals(NavigationRoutes.GO_TO_TRANSACTION_INPUT_PAGE, result);
        }
    }

    @Test
    void testWithValidInput(){
        try (MockedStatic<TextScanner> mockedTextScanner = mockStatic(TextScanner.class)) {
            // Arrange
            Scanner scannerMock = mock(Scanner.class);
            when(scannerMock.nextLine()).thenReturn("I");
            mockedTextScanner.when(TextScanner::getScanner).thenReturn(scannerMock);

            // Act
            NavigationRoutes result = InputMenuOptionReader.showAndReceiveInput();

            // Assert
            assertEquals(NavigationRoutes.GO_TO_DEFINE_INTEREST_RULES_PAGE, result);
        }
    }
}