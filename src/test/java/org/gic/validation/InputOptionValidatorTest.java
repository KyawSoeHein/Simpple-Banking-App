package org.gic.validation;

import org.gic.constants.MenuConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputOptionValidatorTest {
    @Test
    void testInputIsNull() {
        //Arrange
        String input = null;

        //Act & Assert
        assertThrows(Exception.class, () -> InputOptionValidator.validate(input));
    }

    @Test
    void testInputIsBlank() {
        //Arrange
        String input = " ";

        //Act & Assert
        assertThrows(Exception.class, () -> InputOptionValidator.validate(input));
    }

    @Test
    void testInputIsValid() {
        //Arrange
        String input = MenuConstants.DEFINE_INTEREST_RULES_TYPE;

        //Act & Assert
        assertDoesNotThrow(() -> InputOptionValidator.validate(input));
    }

    @Test
    void testInputIsInValid() {
        //Arrange
        String input = "Y";

        //Act & Assert
        assertThrows(Exception.class, () -> InputOptionValidator.validate(input));
    }

}