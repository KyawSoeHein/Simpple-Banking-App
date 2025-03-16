package org.gic.singleton;

import java.util.concurrent.atomic.AtomicInteger;

public class TransactionIdGenerator {
    private static final AtomicInteger transactionId = new AtomicInteger(0);

    public static String getNextTransactionId() {
        return String.format("%02d", transactionId.incrementAndGet());
    }
}
