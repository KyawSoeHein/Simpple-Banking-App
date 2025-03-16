package org.gic.singleton;

import org.gic.constants.DateConstants;
import org.gic.model.InterestRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

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

}