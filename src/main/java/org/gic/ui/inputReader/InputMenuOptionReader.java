package org.gic.ui.inputReader;

import org.gic.constants.MenuConstants;
import org.gic.enums.NavigationRoutes;
import org.gic.exception.BlankInputException;
import org.gic.singleton.InterestRuleStorage;
import org.gic.singleton.TextScanner;
import org.gic.validation.InputOptionValidator;

public class InputMenuOptionReader {
    private static NavigationRoutes routeToAppropriatePage(String optionType) throws IllegalArgumentException {
        return switch (optionType.toUpperCase()) {
            case MenuConstants.DEFINE_INTEREST_RULES_TYPE -> NavigationRoutes.GO_TO_DEFINE_INTEREST_RULES_PAGE;
            case MenuConstants.INPUT_TRANSACTION_TYPE -> NavigationRoutes.GO_TO_TRANSACTION_INPUT_PAGE;
            case MenuConstants.PRINT_STATEMENT_TYPE -> NavigationRoutes.GO_TO_PRINTING_STATEMENT_PAGE;
            case MenuConstants.CALCULATE_INTEREST_TYPE -> routeToCalculateInterestPage();
            case MenuConstants.QUIT_TYPE -> NavigationRoutes.QUIT;
            default -> throw new IllegalStateException("InputOptionReader : Invalid optionType: " + optionType);
        };
    }

    private static NavigationRoutes routeToCalculateInterestPage() throws IllegalArgumentException {
        InterestRuleStorage.creditInterestToAllAccount();
        System.out.println("All accounts have been credited with interest. Please check your account");
        System.out.println();
        return NavigationRoutes.GO_TO_MENU_PAGE;
    }

    public static NavigationRoutes showAndReceiveInput() {
        try {
            System.out.println();
            System.out.print("Enter your option: ");
            String optionType = TextScanner.getScanner().nextLine();
            InputOptionValidator.validate(optionType);
            return routeToAppropriatePage(optionType);
        } catch (BlankInputException blankInputException) {
            System.out.println();
            return NavigationRoutes.GO_TO_MENU_PAGE;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            return NavigationRoutes.GO_TO_MENU_PAGE;
        }
    }
}
