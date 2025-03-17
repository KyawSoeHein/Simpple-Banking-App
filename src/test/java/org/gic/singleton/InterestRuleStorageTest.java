package org.gic.singleton;

import org.gic.constants.DateConstants;
import org.gic.enums.TransactionType;
import org.gic.model.InterestRule;
import org.gic.model.TransactionDetail;
import org.gic.validation.TxnBusinessValidatorNCommiter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InterestRuleStorageTest {

    @AfterEach
    void setUp() {
        InterestRuleStorage.getInterestRuleStorage().clear();
    }

    @Test
    void testAddNewRuleWhileEmpty() {
        //Arrange
        InterestRule interestRule = new InterestRule(LocalDate.now(), "Rule-01", 10.00f);

        //Act
        InterestRuleStorage.addRule(interestRule);

        //Arrange
        assert InterestRuleStorage.getInterestRuleStorage().containsKey(interestRule.ruleId());
        assertEquals(1, InterestRuleStorage.getInterestRuleStorage().size());
    }

    @Test
    void testAddTwoNewRules() {
        //Arrange
        InterestRule interestRule = new InterestRule(LocalDate.parse("20251230", DateConstants.INTEREST_RULE_DATE_FORMATTER), "Rule-01", 10.00f);
        InterestRule secondInterestRule = new InterestRule(LocalDate.parse("20231230", DateConstants.INTEREST_RULE_DATE_FORMATTER), "Rule-02", 10.00f);

        //Act
        InterestRuleStorage.addRule(interestRule);
        InterestRuleStorage.addRule(secondInterestRule);

        //Arrange
        assertEquals(2, InterestRuleStorage.getInterestRuleStorage().size());
    }

    @Test
    void testAddNewRuleWhileRuleIdAlreadyExistButWithDifferentDate() {
        //Arrange
        InterestRule interestRule = new InterestRule(LocalDate.parse("20251230", DateConstants.INTEREST_RULE_DATE_FORMATTER), "Rule-01", 10.00f);
        InterestRule overWrittenRule = new InterestRule(LocalDate.parse("20251130", DateConstants.INTEREST_RULE_DATE_FORMATTER), "Rule-01", 10.00f);

        //Act
        InterestRuleStorage.addRule(interestRule);
        InterestRuleStorage.addRule(overWrittenRule);

        //Arrange
        assert InterestRuleStorage.getInterestRuleStorage().containsKey(interestRule.ruleId());
        assertEquals(1, InterestRuleStorage.getInterestRuleStorage().size());
        assertEquals("Rule-01", InterestRuleStorage.getInterestRuleStorage().get(interestRule.ruleId()).ruleId());
    }

    @Test
    void testAddNewRuleWhileRuleIdAlreadyExistWithSameDate() {
        //Arrange
        InterestRule interestRule = new InterestRule(LocalDate.parse("20251230", DateConstants.INTEREST_RULE_DATE_FORMATTER), "Rule-01", 10.00f);
        InterestRule overWrittenRule = new InterestRule(LocalDate.parse("20251230", DateConstants.INTEREST_RULE_DATE_FORMATTER), "Rule-02", 20.00f);

        //Act
        InterestRuleStorage.addRule(interestRule);
        InterestRuleStorage.addRule(overWrittenRule);

        //Arrange
        assertEquals(1, InterestRuleStorage.getInterestRuleStorage().size());
        assertNull(InterestRuleStorage.getInterestRuleStorage().get(interestRule.ruleId()));
        assertNotNull(InterestRuleStorage.getInterestRuleStorage().get(overWrittenRule.ruleId()));
        assertEquals("Rule-02", InterestRuleStorage.getInterestRuleStorage().get(overWrittenRule.ruleId()).ruleId());
    }

    @Test
    void testCalculateInterest() {
        //Arrange
        InterestRule interestRule = new InterestRule(LocalDate.parse("20251130", DateConstants.INTEREST_RULE_DATE_FORMATTER), "Rule-01", 10.00f);
        InterestRule overWrittenRule = new InterestRule(LocalDate.parse("20251231", DateConstants.INTEREST_RULE_DATE_FORMATTER), "Rule-02", 20.00f);
        TransactionDetail firstDeposit = new TransactionDetail(LocalDate.parse("20251030", DateConstants.TRANSACTION_DATE_FORMATTER), "12345", TransactionType.DEPOSIT, new BigDecimal("100.00"), "1111-11", 'D', BigDecimal.ZERO);
        TransactionDetail secondDeposit = new TransactionDetail(LocalDate.parse("20251230", DateConstants.TRANSACTION_DATE_FORMATTER), "12345", TransactionType.DEPOSIT, new BigDecimal("100.00"), "1111-12", 'D', BigDecimal.ZERO);

        //Act
        InterestRuleStorage.addRule(interestRule);
        InterestRuleStorage.addRule(overWrittenRule);
        TxnBusinessValidatorNCommiter.commitTransaction(firstDeposit);
        TxnBusinessValidatorNCommiter.commitTransaction(secondDeposit);
        InterestRuleStorage.creditInterestToAllAccount();

        //Assert
        assertEquals(1, AccountStorage.getAccountStorage().get("12345").getAccountStatementList().get(LocalDate.now()).size());
    }

}