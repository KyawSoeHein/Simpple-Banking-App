package org.gic.model;

import java.math.BigDecimal;
import java.util.List;

public record Account(String accountNumber, BigDecimal finalAmount, List<AccountStatement> accountStatementList, List<InterestRules> appliedInterestRules) {
}
