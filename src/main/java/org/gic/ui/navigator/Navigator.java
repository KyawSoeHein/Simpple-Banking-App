package org.gic.ui.navigator;

import org.gic.enums.NavigationRoutes;
import org.gic.model.Account;
import org.gic.ui.pages.AccountStatementPage;
import org.gic.ui.pages.DefineInterestRulePage;
import org.gic.ui.pages.TransactionPage;
import org.gic.ui.pages.MenuPage;

public class Navigator {
    private static void routeToAppropriatePage(NavigationRoutes navigationRoute, Account account) {
        switch (navigationRoute) {
            case GO_TO_MENU_PAGE -> MenuPage.showMenuPage();
            case GO_TO_ACCOUNT_STATEMENT_PAGE -> AccountStatementPage.showAccountStatementPage(account);
            case GO_TO_DEFINE_INTEREST_RULES_PAGE -> DefineInterestRulePage.showDefineInterestRulePage();
            case GO_TO_TRANSACTION_INPUT_PAGE -> TransactionPage.showInputTransactionPage();
            case QUIT -> System.exit(0);
            default -> {
                System.out.println("I don't know this route but let's just go to menu first " + navigationRoute);
                MenuPage.showMenuPage();
            }
        }
    }


    public static void goTo(NavigationRoutes navigationRoute) {
        if (navigationRoute == null) {
            MenuPage.showMenuPage();
            return;
        }

        routeToAppropriatePage(navigationRoute, null);
    }

    public static void goTo(NavigationRoutes navigationRoute, Account account) {
        if (navigationRoute == null) {
            MenuPage.showMenuPage();
            return;
        }

        routeToAppropriatePage(navigationRoute, account);
    }
}
