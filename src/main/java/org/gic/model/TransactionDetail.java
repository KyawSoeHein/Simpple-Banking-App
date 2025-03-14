package org.gic.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionDetail(String transactionDate, String accountNumber, TransactionType transactionType, BigDecimal amount, String transactionId, char shortTransactionType) {
}
