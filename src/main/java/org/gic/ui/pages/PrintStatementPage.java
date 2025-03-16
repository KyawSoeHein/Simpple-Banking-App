package org.gic.ui.pages;

import org.gic.enums.NavigationRoutes;
import org.gic.model.Account;
import org.gic.ui.components.InputAccountNumber;
import org.gic.ui.navigator.Navigator;

public class PrintStatementPage {
    public static void showPrintStatementPage() {
        try {
            Account account = InputAccountNumber.readAccountNumber();
            Navigator.goTo(NavigationRoutes.GO_TO_ACCOUNT_STATEMENT_PAGE, account);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            Navigator.goTo(NavigationRoutes.GO_TO_MENU_PAGE);
        }

    }
}
