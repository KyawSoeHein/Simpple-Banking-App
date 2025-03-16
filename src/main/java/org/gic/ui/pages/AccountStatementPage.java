package org.gic.ui.pages;

import org.gic.enums.NavigationRoutes;
import org.gic.model.Account;
import org.gic.model.TransactionDetail;
import org.gic.ui.navigator.Navigator;

//Page does not include any business logic. Just showing UI and navigating
public class AccountStatementPage {
    public static void showAccountStatementPage(Account account) {
        if (account == null || account.getAccountNumber() == null) {
            System.out.println("I received null account, let's go back to menu");
            Navigator.goTo(NavigationRoutes.GO_TO_MENU_PAGE);
            return;
        }

        System.out.println();
        System.out.println("Account: " + account.getAccountNumber());
        System.out.println("Your current balance: " + account.getBalance());
        System.out.printf("| %-10s | %-12s | %-10s | %-7s |\n", "Date", "Txn Id", "Type", "Amount");

        for (TransactionDetail detail: account.getAccountStatementList()) {
            System.out.printf("| %-10s | %-12s | %-10s | %-7s |\n", detail.transactionDate(), detail.transactionId(), detail.transactionType(), detail.amount());
        }

        System.out.println();
        Navigator.goTo(NavigationRoutes.GO_TO_MENU_PAGE);
    }
}
