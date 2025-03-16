package org.gic.validation;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class InputTxnFmtValidatorNTransformerTest {

    @Test
    void testWithNull() {
        //Arrange
        String input = null;

        //Act & Assert
        assertThrows(Exception.class, () -> InputTxnFmtValidatorNTransformer.validateAndTransform(input));
    }

    @Test
    void testWithInValidEmptyStr() {
        //Arrange
        String input = "";

        //Act & Assert
        assertThrows(Exception.class, () -> InputTxnFmtValidatorNTransformer.validateAndTransform(input));
    }

    @Test
    void testWithInValidBlankStr() {
        //Arrange
        String input = " ";

        //Act & Assert
        assertThrows(Exception.class, () -> InputTxnFmtValidatorNTransformer.validateAndTransform(input));
    }

    @Test
    void testWithInValidSpacesInput() {
        //Arrange
        String input = "111113333355555 33333";

        //Act & Assert
        assertThrows(Exception.class, () -> InputTxnFmtValidatorNTransformer.validateAndTransform(input));
    }

    @Test
    void testWithInValidInputExtraSpace() {
        //Arrange
        String input = "11111  33333 55555 33333";

        //Act & Assert
        assertThrows(Exception.class, () -> InputTxnFmtValidatorNTransformer.validateAndTransform(input));
    }

    @Test
    void testWithValidInput() {
        //Arrange
        String input = "20251230 33333 D 33.00";

        //Act & Assert
        assertDoesNotThrow(() -> InputTxnFmtValidatorNTransformer.validateAndTransform(input));
    }

    @Test
    void testWithInValidDateInCorrect() {
        //Arrange
        String input = "0251230 33333 YYY 33.000";

        //Act & Assert
        assertThrows(DateTimeParseException.class, () -> InputTxnFmtValidatorNTransformer.validateAndTransform(input));
    }

    @Test
    void testWithValidDate() {
        //Arrange
        String input = "20251230 33333 D 33.00";

        //Act & Assert
        assertDoesNotThrow(() -> InputTxnFmtValidatorNTransformer.validateAndTransform(input));
    }

    @Test
    void testWithInValidAccountNumber() {
        //Arrange
        String input = "20251230 Y 33.00";

        //Act & Assert
        assertThrows(Exception.class, () -> InputTxnFmtValidatorNTransformer.validateAndTransform(input));
    }

    @Test
    void testWithInValidTransactionType() {
        //Arrange
        String input = "20251230 33333 Y 33.00";

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> InputTxnFmtValidatorNTransformer.validateAndTransform(input));
    }

    @Test
    void testWithValidTransactionType() {
        //Arrange
        String input = "20251230 33333 D 33.00";

        //Act & Assert
        assertDoesNotThrow(() -> InputTxnFmtValidatorNTransformer.validateAndTransform(input));
    }


    @Test
    void testWithInValidAmountAndBigDecimalConversionFailed() {
        //Arrange
        String input = "20251230 33333 D AAAAA";

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> InputTxnFmtValidatorNTransformer.validateAndTransform(input));
    }

    @Test
    void testWithInvalidAmountDecimal() {
        //Arrange
        String input = "20251230 33333 AAA 33.000";

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> InputTxnFmtValidatorNTransformer.validateAndTransform(input));
    }

    @Test
    void testWithValidAmountZero() {
        //Arrange
        String input = "20251230 33333 AAA 00.00";

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> InputTxnFmtValidatorNTransformer.validateAndTransform(input));
    }

    @Test
    void testWithValidAmountNegative() {
        //Arrange
        String input = "20251230 33333 AAA -33.00";

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> InputTxnFmtValidatorNTransformer.validateAndTransform(input));
    }

    @Test
    void testWithValidAmount() {
        //Arrange
        String input = "20251230 33333 AAA 33.00";

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> InputTxnFmtValidatorNTransformer.validateAndTransform(input));
    }

}