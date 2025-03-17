package org.gic.model;

import org.gic.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionDetail(LocalDate transactionDate, String accountNumber, TransactionType transactionType, BigDecimal amount, String transactionId, char shortTransactionType, BigDecimal balanceAfterTransaction) {
}
