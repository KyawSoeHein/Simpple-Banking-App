package org.gic.ui.components;

import org.gic.enums.NavigationRoutes;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputOptionReaderTest {
    void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    void testWithValidInput(){
        //Arrange
        provideInput("T");

        //Act
        NavigationRoutes result = InputOptionReader.showAndReceiveInput();

        //Assert
        assertEquals(NavigationRoutes.GO_TO_TRANSACTION_INPUT_PAGE, result);
    }

    @Test
    void testWithInValidInput(){
        //Arrange
        provideInput("Y");

        //Act
        NavigationRoutes result = InputOptionReader.showAndReceiveInput();

        //Assert
        assertEquals(NavigationRoutes.GO_TO_MENU_PAGE, result);
    }

    @Test
    void testWithInValidInputBlank(){
        //Arrange
        provideInput("   ");

        //Act
        NavigationRoutes result = InputOptionReader.showAndReceiveInput();

        //Assert
        assertEquals(NavigationRoutes.GO_TO_MENU_PAGE, result);
    }

}