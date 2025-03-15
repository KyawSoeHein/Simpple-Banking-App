package org.gic.model;

import org.gic.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountStatement(LocalDate transactionDate, String transactionId, TransactionType transactionType, BigDecimal amount) {
}
