package org.gic.ui.pages;

import org.gic.enums.NavigationRoutes;
import org.gic.ui.components.InputTransactionDetailReader;
import org.gic.ui.navigator.Navigator;

//Page does not include any business logic. Just showing UI and navigating
public class TransactionPage {
    public static void showInputTransactionPage() {
        try {
            InputTransactionDetailReader.readTransactionDetails();
            Navigator.goTo(NavigationRoutes.GO_TO_ACCOUNT_STATEMENT_PAGE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            Navigator.goTo(NavigationRoutes.GO_TO_MENU_PAGE);
        }
    }
}
