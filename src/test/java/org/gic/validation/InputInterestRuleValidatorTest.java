package org.gic.validation;

import org.gic.constants.DateConstants;
import org.gic.model.InterestRule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class InputInterestRuleValidatorTest {

    @Test
    void testDateFormatWithValid() {
        //Arrange
        String interestRule = "20250123 0000 10.00";

        //Act & Assert
        assertDoesNotThrow(() -> InputInterestRuleValidator.validateAndTransform(interestRule));
    }

    @Test
    void testDateFormatWithInValid() {
        //Arrange
        String interestRule = "01032025 0000 10.00";

        //Act & Assert
        assertThrows(DateTimeParseException.class, () -> InputInterestRuleValidator.validateAndTransform(interestRule));
    }

    @Test
    void testDateFormatWithCorrectFormatButInvalidDate() {
        //Arrange
        String interestRule = "20250230 0000 10.00";

        //Act & Assert
        assertThrows(DateTimeParseException.class, () -> InputInterestRuleValidator.validateAndTransform(interestRule));
    }

    @Test
    void testInterestRateWithValidData() {
        //Arrange
        String interestRule = "20250123 0000 10.00";

        //Act & Assert
        assertDoesNotThrow(() -> InputInterestRuleValidator.validateAndTransform(interestRule));
    }

    @Test
    void testInterestRateWithInValidData() {
        //Arrange
        String interestRule = "20250123 0000 -10.0";

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> InputInterestRuleValidator.validateAndTransform(interestRule));
    }

    @Test
    void testInterestRateInValidZero() {
        //Arrange
        String interestRule = "20250123 0000 0.00";

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> InputInterestRuleValidator.validateAndTransform(interestRule));
    }

    @Test
    void testInterestRateInValidHundred() {
        //Arrange
        String interestRule = "20250123 0000 100";

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> InputInterestRuleValidator.validateAndTransform(interestRule));
    }

    @Test
    void testIfCorrectDTOIsReturned() throws Exception {
        //Arrange
        InterestRule expected = new InterestRule(LocalDate.parse("20250123", DateConstants.INTEREST_RULE_DATE_FORMATTER), "0000", 10.00f);
        String interestRule = "20250123 0000 10.00";

        //Act
        InterestRule actual = InputInterestRuleValidator.validateAndTransform(interestRule);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void testInterestRateInValidNull() {
        //Arrange
        String interestRule = null;

        //Act & Assert
        assertThrows(NullPointerException.class, () -> InputInterestRuleValidator.validateAndTransform(interestRule));
    }

}