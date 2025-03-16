package org.gic.ui.pages;

import org.gic.constants.MenuConstants;
import org.gic.enums.NavigationRoutes;
import org.gic.ui.inputReader.InputOptionReader;
import org.gic.ui.navigator.Navigator;

//Page does not include any business logic. Just showing UI and navigating
public class MenuPage {
    public static void showMenuPage() {
        System.out.println("Welcome to AwesomeGIC Bank! What would you like to do?");
        System.out.println(" [" + MenuConstants.INPUT_TRANSACTION_TYPE + "] " + MenuConstants.INPUT_TRANSACTIONS_MESSAGE);
        System.out.println(" [" + MenuConstants.DEFINE_INTEREST_RULES_TYPE + "] " + MenuConstants.DEFINE_INTEREST_RULES_MESSAGE);
        System.out.println(" [" + MenuConstants.PRINT_STATEMENT_TYPE + "] " + MenuConstants.PRINT_STATEMENT_MESSAGE);
        System.out.println(" [" + MenuConstants.QUIT_TYPE + "] " + MenuConstants.QUIT_MESSAGE);

        NavigationRoutes nextRoute = InputOptionReader.showAndReceiveInput();
        Navigator.goTo(nextRoute);
    }
}
