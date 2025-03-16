package org.gic.ui.pages;

import org.gic.enums.NavigationRoutes;
import org.gic.model.InterestRule;
import org.gic.singleton.InterestRuleStorage;
import org.gic.ui.navigator.Navigator;

import java.util.HashMap;

//Page does not include any business logic. Just showing UI and navigating
public class InterestRuleListPage {
    public static void showInterestRuleListPage() {
        HashMap<String, InterestRule> rules = InterestRuleStorage.getInterestRuleStorage();
        if (rules == null || rules.isEmpty()) {
            System.out.println("There is no interest rule to show");
            Navigator.goTo(NavigationRoutes.GO_TO_MENU_PAGE);
            return;
        }

        System.out.println();
        System.out.println("Interest rules:");
        System.out.printf("| %-10s | %-12s | %-10s |\n", "Date", "RuleId", "Rate (%)");

        for (String ruleId: rules.keySet()) {
            InterestRule detail = rules.get(ruleId);
            System.out.printf("| %-10s | %-12s | %-10s |\n", detail.ruleInsertedDate(), detail.ruleId(), detail.interestRateInPercent());
        }

        System.out.println();
        Navigator.goTo(NavigationRoutes.GO_TO_MENU_PAGE);
    }
}
