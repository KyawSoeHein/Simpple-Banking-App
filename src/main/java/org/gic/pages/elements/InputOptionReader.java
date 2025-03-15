package org.gic.pages.elements;

import org.gic.constants.MenuConstants;
import org.gic.enums.NavigationRoutes;
import org.gic.exception.BlankInputException;
import org.gic.validation.InputOptionValidator;

import java.util.Scanner;

public class InputOptionReader {
    public static Scanner scanner = new Scanner(System.in);

    private static NavigationRoutes routeToAppropriatePage (String optionType) {
        return switch (optionType) {
            case MenuConstants.DEFINE_INTEREST_RULES_TYPE -> NavigationRoutes.GO_TO_DEFINE_INTEREST_RULES_PAGE;
            case MenuConstants.INPUT_TRANSACTION_TYPE -> NavigationRoutes.GO_TO_TRANSACTION_INPUT_PAGE;
            case MenuConstants.PRINT_STATEMENT_TYPE -> NavigationRoutes.GO_TO_ACCOUNT_STATEMENT_PAGE;
            case MenuConstants.QUIT_MESSAGE -> NavigationRoutes.QUIT;
            default -> throw new IllegalStateException("InputOptionReader : Invalid optionType: " + optionType);
        };
    }

    public static NavigationRoutes showAndReceiveInput() {
        try {
            System.out.println();
            System.out.print("Enter your option: ");
            String optionType = scanner.nextLine();
            InputOptionValidator.validate(optionType);
            return routeToAppropriatePage(optionType);
        } catch (BlankInputException blankInputException) {
            return NavigationRoutes.GO_TO_MENU_PAGE;
        } catch (Exception e) {
            System.out.println("Seems like you input invalid option. Please try again.");
            System.out.println();
            return NavigationRoutes.GO_TO_MENU_PAGE;
        }
    }
}
