package org.gic.validation;

import org.gic.model.InterestRule;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.gic.constants.DateConstants.INTEREST_RULE_DATE_FORMATTER;

public class InputInterestRuleValidator {
    private static final String interestRuleRegex = "^(\\S+)\\s(\\S+)\\s(\\S+)$";
    private static final Pattern interestRuleCheckerPattern = Pattern.compile(interestRuleRegex);

    private static LocalDate isDateFormatValid(String ruleInsertedDate) throws DateTimeParseException {
        try {
            return LocalDate.parse(ruleInsertedDate, INTEREST_RULE_DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Date needs to be in valid range and YYYYMMdd format", ruleInsertedDate, 0);
        }
    }

    private static float isInterestRateValid(float interestRate) throws IllegalArgumentException {
        if (interestRate <= 0 || interestRate >= 100) {
            throw new IllegalArgumentException("Interest rate should be between 0 and 100");
        }

        return interestRate;
    }

    private static void validateInputFormat(Matcher matcher) throws Exception {
        if (!matcher.matches()) {
            throw new Exception("InterestRuleTransformer : Invalid input format. Please input with <Date> <RuleId> <Rate in %> format");
        }
    }

    public static InterestRule validateAndTransform(String input) throws Exception {
        Matcher matcher = interestRuleCheckerPattern.matcher(input);
        validateInputFormat(matcher);

        LocalDate ruleInsertedDate = isDateFormatValid(matcher.group(1));
        String ruleId = matcher.group(2);
        float interestRate = isInterestRateValid(Float.parseFloat(matcher.group(3)));

        return new InterestRule(ruleInsertedDate, ruleId, interestRate);
    }
}
