package org.gic.enums;

import lombok.Getter;
import org.gic.constants.TransactionTypeConstants;

@Getter
public enum TransactionType {
    DEPOSIT(TransactionTypeConstants.TRANSACTION_TYPE_DEPOSIT),
    WITHDRAWAL(TransactionTypeConstants.TRANSACTION_TYPE_WITHDRAW),
    INTEREST_EARNED(TransactionTypeConstants.TRANSACTION_TYPE_INTEREST);

    private final String code;
    TransactionType(String code) {
        this.code = code;
    }

    public static TransactionType fromCode(String code) throws IllegalArgumentException {
        for (TransactionType transactionType : TransactionType.values()) {
            if (transactionType.getCode().equalsIgnoreCase(code)) {
                return transactionType;
            }
        }

        throw new IllegalArgumentException("You added invalid transactionType indicator. Please add either D or W (case insensitive). Current value: " + code);
    }
}
