package org.gic.singleton;

import java.util.concurrent.atomic.AtomicInteger;

public class TransactionIdGenerator {
    private static final AtomicInteger depositTransactionId = new AtomicInteger(0);
    private static final AtomicInteger withdrawTransactionId = new AtomicInteger(0);

    public static String getNextDepositTransactionId() {
        return String.format("%02d", depositTransactionId.incrementAndGet());
    }

    public static String getNextWithdrawTransactionId() {
        return String.format("%02d", withdrawTransactionId.incrementAndGet());
    }
}
