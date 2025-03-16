package org.gic.ui.components;

import org.gic.constants.MenuConstants;
import org.gic.constants.MessageConstants;

public class MenuOptions {
    public static void showOptions() {
        System.out.println(MessageConstants.GREETING_MESSAGE);
        System.out.println(" [" + MenuConstants.INPUT_TRANSACTION_TYPE + "] " + MenuConstants.INPUT_TRANSACTIONS_MESSAGE);
        System.out.println(" [" + MenuConstants.DEFINE_INTEREST_RULES_TYPE + "] " + MenuConstants.DEFINE_INTEREST_RULES_MESSAGE);
        System.out.println(" [" + MenuConstants.PRINT_STATEMENT_TYPE + "] " + MenuConstants.PRINT_STATEMENT_MESSAGE);
        System.out.println(" [" + MenuConstants.QUIT_TYPE + "] " + MenuConstants.QUIT_MESSAGE);
    }
}
