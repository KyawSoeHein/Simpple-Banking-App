package org.gic.singleton;

import org.apache.commons.lang3.StringUtils;
import org.gic.constants.TransactionTypeConstants;
import org.gic.enums.TransactionType;
import org.gic.model.Account;
import org.gic.model.InterestRule;
import org.gic.model.TransactionDetail;
import org.gic.validation.TxnBusinessValidatorNCommiter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class InterestRuleStorage {
    private static final HashMap<String, InterestRule> interestRules = new HashMap<>();
    private static final TreeMap<LocalDate, String> ruleDates = new TreeMap<>();

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

    private static InterestRule getEffectiveInterestRule(LocalDate transactionDate) {
        for (LocalDate date: ruleDates.keySet()) {
            if (date.isBefore(transactionDate)) {
                return interestRules.get(ruleDates.get(date));
            }
        }

        //it is so sad that people don't get interest because there is no rule defined. Everyone gets 1% by default
        return new InterestRule(LocalDate.now(), "default-rule", 1.0f);
    }

    private static long getDaysBetweenCurrentRuleAndNextRule(InterestRule interestRule) {
        Map<LocalDate, String> nextRuleList = ruleDates.tailMap(interestRule.ruleInsertedDate(), false);

        if (nextRuleList.isEmpty()) {
            return 0;
        }
        InterestRule nextRule = interestRules.get(nextRuleList.entrySet().iterator().next().getValue());
        return ChronoUnit.DAYS.between(interestRule.ruleInsertedDate(), nextRule.ruleInsertedDate());
    }

    private static long getDaysBetweenCurrentTransactionAndNextTransaction(TreeMap<LocalDate, List<TransactionDetail>> accountStatementList, TransactionDetail currentTransaction) {
        Map<LocalDate, List<TransactionDetail>> nextTransactionList = accountStatementList.tailMap(currentTransaction.transactionDate(), false);

        if (nextTransactionList.isEmpty()) {
            return 0;
        }

        TransactionDetail nextTransaction = nextTransactionList.entrySet().iterator().next().getValue().getFirst();
        return ChronoUnit.DAYS.between(currentTransaction.transactionDate(), nextTransaction.transactionDate());
    }

    private static long dayToCalculateInterest(Account account, InterestRule currentEffectiveRule, TransactionDetail currentTransaction) {
        long daysBetweenCurrentRuleAndNextRule = getDaysBetweenCurrentRuleAndNextRule(currentEffectiveRule);
        long daysBetweenCurrentTransactionAndNextTransaction = getDaysBetweenCurrentTransactionAndNextTransaction(account.getAccountStatementList(), currentTransaction);
        return daysBetweenCurrentRuleAndNextRule == 0 ? daysBetweenCurrentTransactionAndNextTransaction
                : daysBetweenCurrentTransactionAndNextTransaction == 0 ? daysBetweenCurrentRuleAndNextRule
                : Math.min(daysBetweenCurrentRuleAndNextRule, daysBetweenCurrentTransactionAndNextTransaction);
    }

    private static BigDecimal calculateInterest(Account account) {
        if (account == null || account.getAccountStatementList().isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal interest = BigDecimal.ZERO;
        for (Map.Entry<LocalDate, List<TransactionDetail>> entry : account.getAccountStatementList().entrySet()) {
            for (TransactionDetail detail : entry.getValue()) {
                if (detail.transactionType() != TransactionType.WITHDRAWAL && detail.transactionType() != TransactionType.DEPOSIT) {
                    continue;
                }

                InterestRule effectiveInterestRule = getEffectiveInterestRule(detail.transactionDate());
                long daysToCalculateInterest = dayToCalculateInterest(account, effectiveInterestRule, detail);
                BigDecimal interestToAdd = detail.balanceAfterTransaction()
                        .multiply(BigDecimal.valueOf(effectiveInterestRule.interestRateInPercent())
                                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP))
                        .multiply(BigDecimal.valueOf(daysToCalculateInterest));

                interest = interest.add(interestToAdd);
            }
        }

        return interest.divide(new BigDecimal("365"), 2, RoundingMode.HALF_UP);
    }

    public static void creditInterestToAllAccount() throws IllegalArgumentException {
        HashMap<String, Account> accountHashMap = AccountStorage.getAccountStorage();

        if (accountHashMap.isEmpty()) {
            throw new IllegalArgumentException("No account to be credited");
        }
        for (Map.Entry<String, Account> entry : accountHashMap.entrySet()) {
            Account account = entry.getValue();
            BigDecimal interest = calculateInterest(account);
            TxnBusinessValidatorNCommiter.commitTransaction(new TransactionDetail(LocalDate.now(), account.getAccountNumber(), TransactionType.INTEREST_EARNED,
                    interest, StringUtils.EMPTY, TransactionTypeConstants.TRANSACTION_TYPE_INTEREST.charAt(0), account.getBalance().add(interest)
            ));
        }
    }
}
