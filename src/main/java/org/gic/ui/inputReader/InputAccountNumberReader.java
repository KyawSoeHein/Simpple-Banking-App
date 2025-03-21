package org.gic.ui.inputReader;

import org.gic.model.Account;
import org.gic.singleton.AccountStorage;
import org.gic.singleton.TextScanner;

public class InputAccountNumberReader {
    public static Account readAccountNumber() throws Exception {
        System.out.print("Enter your account number: ");
        String accountNumberStr = TextScanner.getScanner().nextLine();
        Account account = AccountStorage.getAccountStorage().get(accountNumberStr);

        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }

        return account;
    }
}
