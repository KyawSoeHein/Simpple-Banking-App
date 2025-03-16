package org.gic.singleton;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionIdGeneratorTest {
    @Test
    void testGenerateId() {
        assertEquals("01", TransactionIdGenerator.getNextTransactionId());
        assertEquals("02", TransactionIdGenerator.getNextTransactionId());

        for (int i = 0; i < 8; i++) {
            TransactionIdGenerator.getNextTransactionId();
        }

        assertEquals("11", TransactionIdGenerator.getNextTransactionId());
        assertEquals("12", TransactionIdGenerator.getNextTransactionId());
    }
}