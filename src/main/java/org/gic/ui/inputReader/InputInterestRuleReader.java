package org.gic.ui.inputReader;

import org.gic.model.InterestRule;
import org.gic.singleton.InterestRuleStorage;
import org.gic.singleton.TextScanner;
import org.gic.validation.InputInterestRuleValidator;

public class InputInterestRuleReader {
    public static void readInterestRule() throws Exception {
        System.out.print("Enter your interest rule: ");
        String interestRuleStr = TextScanner.getScanner().nextLine();
        InterestRule interestRule = InputInterestRuleValidator.validateAndTransform(interestRuleStr);
        InterestRuleStorage.addRule(interestRule);
    }
}
