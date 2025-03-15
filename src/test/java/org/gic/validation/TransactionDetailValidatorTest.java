package org.gic.validation;

import org.gic.model.TransactionDetail;
import org.gic.enums.TransactionType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDetailValidatorTest {

    @Test
    void testDateFormatWithValid() {
        //Arrange
        String date= "20250123";
        TransactionDetail transactionDetail = new TransactionDetail(date, "11111", TransactionType.DEPOSIT,
                new BigDecimal("100"), "122222", 'D');

        //Act & Assert
        assertDoesNotThrow(() -> TransactionDetailValidator.validate(transactionDetail));
    }

    @Test
    void testDateFormatWithInValid() {
        //Arrange
        String date= "01032025";
        TransactionDetail transactionDetail = new TransactionDetail(date, "11111", TransactionType.DEPOSIT,
                new BigDecimal("100"), "122222", 'D');

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> TransactionDetailValidator.validate(transactionDetail));
    }

    @Test
    void testDateFormatWithCorrectFormatButInvalidDate() {
        //Arrange
        String date= "20250230";
        TransactionDetail transactionDetail = new TransactionDetail(date, "11111", TransactionType.DEPOSIT,
                new BigDecimal("100"), "122222", 'D');

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> TransactionDetailValidator.validate(transactionDetail));
    }

    @Test
    void testTransactionTypeWithValid() {
        //Arrange
        String shortTransactionType = "D";
        TransactionDetail transactionDetail = new TransactionDetail("20250123", "11111", TransactionType.DEPOSIT,
                new BigDecimal("100"), "122222", 'D');

        //Act & Assert
        assertDoesNotThrow(() -> TransactionDetailValidator.validate(transactionDetail));
    }

    @Test
    void testTransactionTypeWithInValid() {
        //Arrange
        String shortTransactionType = "D";
        TransactionDetail transactionDetail = new TransactionDetail("20250123", "11111", TransactionType.DEPOSIT,
                new BigDecimal("100"), "122222", 'A');

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> TransactionDetailValidator.validate(transactionDetail));
    }

    @Test
    void testAmountWithValid() {
        //Arrange
        BigDecimal amount = new BigDecimal("100");
        TransactionDetail transactionDetail = new TransactionDetail("20250123", "11111", TransactionType.DEPOSIT,
                amount, "122222", 'D');

        //Act & Assert
        assertDoesNotThrow(() -> TransactionDetailValidator.validate(transactionDetail));
    }

    @Test
    void testAmountWithInValidZero() {
        //Arrange
        BigDecimal amount = new BigDecimal("0");
        TransactionDetail transactionDetail = new TransactionDetail("20250123", "11111", TransactionType.DEPOSIT,
                amount, "122222", 'D');

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> TransactionDetailValidator.validate(transactionDetail));
    }

    @Test
    void testAmountWithInValidNegative() {
        //Arrange
        BigDecimal amount = new BigDecimal("-100");
        TransactionDetail transactionDetail = new TransactionDetail("20250123", "11111", TransactionType.DEPOSIT,
                amount, "122222", 'D');

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> TransactionDetailValidator.validate(transactionDetail));
    }


}