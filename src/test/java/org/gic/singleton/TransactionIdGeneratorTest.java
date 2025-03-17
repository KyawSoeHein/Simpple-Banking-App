package org.gic.singleton;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionIdGeneratorTest {
    @Test
    void testGenerateDepositId() {
        assertEquals("01", TransactionIdGenerator.getNextDepositTransactionId());
        assertEquals("02", TransactionIdGenerator.getNextDepositTransactionId());

        for (int i = 0; i < 8; i++) {
            TransactionIdGenerator.getNextDepositTransactionId();
        }

        assertEquals("11", TransactionIdGenerator.getNextDepositTransactionId());
        assertEquals("12", TransactionIdGenerator.getNextDepositTransactionId());
    }

    @Test
    void testGenerateWithdrawId() {
        assertEquals("01", TransactionIdGenerator.getNextWithdrawTransactionId());
        assertEquals("02", TransactionIdGenerator.getNextWithdrawTransactionId());

        for (int i = 0; i < 8; i++) {
            TransactionIdGenerator.getNextWithdrawTransactionId();
        }

        assertEquals("11", TransactionIdGenerator.getNextWithdrawTransactionId());
        assertEquals("12", TransactionIdGenerator.getNextWithdrawTransactionId());
    }
}