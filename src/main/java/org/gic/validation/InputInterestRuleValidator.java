package org.gic.validation;

import org.gic.model.InterestRules;

import java.time.LocalDate;

import static org.gic.constants.DateConstants.INTEREST_RULE_DATE_FORMATTER;

public class InputInterestRuleValidator {
    private static boolean isDateFormatValid(InterestRules interestRules) {
        try {
            LocalDate.parse(interestRules.ruleInsertedDate(), INTEREST_RULE_DATE_FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isInterestRateValid(InterestRules interestRules) {
        return interestRules.interestValueInPercent() > 0 && interestRules.interestValueInPercent() < 100;
    }

    public static void validate(InterestRules interestRules) throws IllegalArgumentException {
        if (!isDateFormatValid(interestRules)) {
            throw new IllegalArgumentException("Invalid interest rule date format");
        }

        if (!isInterestRateValid(interestRules)) {
            throw new IllegalArgumentException("Invalid interest rate");
        }
    }
}
