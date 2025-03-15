package org.gic.pages.navigator;

import org.gic.enums.NavigationRoutes;
import org.gic.pages.components.AccountStatementPage;
import org.gic.pages.components.DefineInterestRulePage;
import org.gic.pages.components.InputTransactionPage;
import org.gic.pages.components.MenuPage;

public class Navigator {
    public static void goTo(NavigationRoutes navigationRoute) {
        if (navigationRoute == null) {
            MenuPage.showMenuPage();
            return;
        }

        switch (navigationRoute) {
            case GO_TO_MENU_PAGE -> MenuPage.showMenuPage();
            case GO_TO_ACCOUNT_STATEMENT_PAGE -> AccountStatementPage.showAccountStatementPage();
            case GO_TO_DEFINE_INTEREST_RULES_PAGE -> DefineInterestRulePage.showDefineInterestRulePage();
            case GO_TO_TRANSACTION_INPUT_PAGE -> InputTransactionPage.showInputTransactionPage();
            case QUIT -> System.exit(0);
            default -> {
                System.out.println("I don't know this route but let's just go to menu first");
                MenuPage.showMenuPage();
            }
        }
    }
}
