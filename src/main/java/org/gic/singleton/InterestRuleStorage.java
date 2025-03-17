package org.gic.singleton;

import org.gic.model.Account;
import org.gic.model.InterestRule;

import java.time.LocalDate;
import java.util.HashMap;

public class InterestRuleStorage {
    private static final HashMap<String, InterestRule> interestRules = new HashMap<>();
    private static final HashMap<LocalDate, String> ruleDates = new HashMap<>();

    public static HashMap<String, InterestRule> getInterestRuleStorage() {
        return interestRules;
    }

    public static void addRule(InterestRule newInterestRule) throws IllegalArgumentException {
        if (ruleDates.containsKey(newInterestRule.ruleInsertedDate())) {
            interestRules.remove(ruleDates.get(newInterestRule.ruleInsertedDate()));
        }

        interestRules.put(newInterestRule.ruleId(), newInterestRule);
        ruleDates.put(newInterestRule.ruleInsertedDate(), newInterestRule.ruleId());
    }

    public static void calculateInterest(Account account) throws IllegalArgumentException {
        if (account.getAccountStatementList().isEmpty()) {
            System.out.println("There is no transaction to calculate interest for " + account.getAccountNumber());
            return;
        }

        
    }
}
