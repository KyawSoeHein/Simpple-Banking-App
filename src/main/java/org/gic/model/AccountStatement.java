package org.gic.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountStatement(LocalDate transactionDate, String transactionId, TransactionType transactionType, BigDecimal amount) {
}
