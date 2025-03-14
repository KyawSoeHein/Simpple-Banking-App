package org.gic.validation;

import org.gic.model.InterestRules;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterestRuleValidatorTest {
    private final InterestRuleValidator interestRuleValidator = new InterestRuleValidator();

    @Test
    void testDateFormatWithValid() {
        //Arrange
        String date= "20250123";
        InterestRules interestRules = new InterestRules(date, "0000", 10.0f);

        //Act & Assert
        assertDoesNotThrow(() -> interestRuleValidator.validate(interestRules));
    }

    @Test
    void testDateFormatWithInValid() {
        //Arrange
        String date= "01032025";
        InterestRules interestRules = new InterestRules(date, "0000", 10.0f);

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> interestRuleValidator.validate(interestRules));
    }

    @Test
    void testDateFormatWithCorrectFormatButInvalidDate() {
        //Arrange
        String date= "20250230";
        InterestRules interestRules = new InterestRules(date, "0000", 10.0f);

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> interestRuleValidator.validate(interestRules));
    }

    @Test
    void testInterestRateWithValid() {
        //Arrange
        float interestRate = 10.0f;
        InterestRules interestRules = new InterestRules("20250123", "0000", interestRate);

        //Act & Assert
        assertDoesNotThrow(() -> interestRuleValidator.validate(interestRules));
    }

    @Test
    void testInterestRateWithInValid() {
        //Arrange
        float interestRate = -10.0f;
        InterestRules interestRules = new InterestRules("20250123", "0000", interestRate);

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> interestRuleValidator.validate(interestRules));
    }

    @Test
    void testInterestRateInValidZero() {
        //Arrange
        float interestRate = 0.0f;
        InterestRules interestRules = new InterestRules("20250123", "0000", interestRate);

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> interestRuleValidator.validate(interestRules));
    }

    @Test
    void testInterestRateInValidHundred() {
        //Arrange
        float interestRate = 100f;
        InterestRules interestRules = new InterestRules("20250123", "0000", interestRate);

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> interestRuleValidator.validate(interestRules));
    }

}