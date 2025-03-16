package org.gic.ui.pages;

import org.gic.enums.NavigationRoutes;
import org.gic.ui.components.InputInterestRuleReader;
import org.gic.ui.navigator.Navigator;

//Page does not include any business logic. Just showing UI and navigating
public class DefineInterestRulePage {
    public static void showDefineInterestRulePage() {
        try {
            InputInterestRuleReader.readInterestRule();
            Navigator.goTo(NavigationRoutes.GO_TO_INTEREST_RULE_LIST_PAGE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            Navigator.goTo(NavigationRoutes.GO_TO_MENU_PAGE);
        }
    }
}
