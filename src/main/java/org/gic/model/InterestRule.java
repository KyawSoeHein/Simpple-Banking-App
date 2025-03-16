package org.gic.model;

import java.time.LocalDate;

public record InterestRule(LocalDate ruleInsertedDate, String ruleId, float interestRateInPercent) {
}
