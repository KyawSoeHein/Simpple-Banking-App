package org.gic.model;

import org.gic.enums.TransactionType;

import java.math.BigDecimal;

public record TransactionDetail(String transactionDate, String accountNumber, TransactionType transactionType, BigDecimal amount, String transactionId, char shortTransactionType) {
}
