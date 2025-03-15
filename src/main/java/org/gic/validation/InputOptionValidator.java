package org.gic.validation;

import org.gic.constants.MenuConstants;
import org.gic.exception.BlankInputException;

import java.util.Set;

public class InputOptionValidator {
    public static void validate(String input) throws Exception {
        Set<String> VALID_INPUTS = Set.of(
                MenuConstants.QUIT_TYPE,
                MenuConstants.DEFINE_INTEREST_RULES_TYPE,
                MenuConstants.PRINT_STATEMENT_TYPE,
                MenuConstants.INPUT_TRANSACTION_TYPE
        );

        if (input == null) {
            throw new Exception("Input cannot be null");
        }

        if (input.isBlank()) {
            throw new BlankInputException("Input cannot be blank");
        }

        if (!VALID_INPUTS.contains(input.toUpperCase())) {
            throw new Exception("Input should be one of the options from menu options");
        }
    }
}
