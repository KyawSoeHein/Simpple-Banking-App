package org.gic.model;

import org.gic.constants.TransactionTypeConstants;

public enum TransactionType {
    DEPOSIT(TransactionTypeConstants.TRANSACTION_TYPE_DEPOSIT),
    WITHDRAWAL(TransactionTypeConstants.TRANSACTION_TYPE_WITHDRAW),
    INTEREST_EARNED(TransactionTypeConstants.TRANSACTION_TYPE_INTEREST);

    private final char code;
    TransactionType(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }

    public TransactionType fromCode(char code) throws IllegalArgumentException {
        for (TransactionType transactionType : TransactionType.values()) {
            if (transactionType.getCode() == code) {
                return transactionType;
            }
        }

        throw new IllegalArgumentException(
                String.format("No enum constant %s.%s", TransactionType.class.getCanonicalName(), code));
    }
}
