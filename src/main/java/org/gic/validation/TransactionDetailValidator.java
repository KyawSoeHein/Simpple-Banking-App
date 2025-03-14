package org.gic.validation;

import org.gic.constants.TransactionTypeConstants;
import org.gic.model.TransactionDetail;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.gic.constants.DateConstants.TRANSACTION_DATE_FORMATTER;

public class TransactionDetailValidator {
    private boolean isDateFormatValid(TransactionDetail transactionDetail) {
        try {
            LocalDate.parse(transactionDetail.transactionDate(), TRANSACTION_DATE_FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTransactionTypeValid(TransactionDetail transactionDetail) {
        return transactionDetail.shortTransactionType() == TransactionTypeConstants.TRANSACTION_TYPE_DEPOSIT ||
                transactionDetail.shortTransactionType() == TransactionTypeConstants.TRANSACTION_TYPE_WITHDRAW;
    }

    private boolean isAmountValid(TransactionDetail transactionDetail) {
        return transactionDetail.amount().compareTo(BigDecimal.ZERO) > 0 &&
                transactionDetail.amount().scale() <= 2;
    }

    public void validate(TransactionDetail transactionDetail) throws IllegalArgumentException {
        if (!isDateFormatValid(transactionDetail)) {
            throw new IllegalArgumentException("Invalid transaction date format");
        }

        if (!isTransactionTypeValid(transactionDetail)) {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        if (!isAmountValid(transactionDetail)) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }

}
