package org.gic.singleton;

import org.gic.model.Account;

import java.util.HashMap;

//I am not considering multi threaded scenario
public class AccountStorage {
    private static final HashMap<String, Account> accounts = new HashMap<>();

    public static HashMap<String, Account> getAccountStorage() {
        return accounts;
    }
}
