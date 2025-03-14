package org.gic.storage;

import org.gic.model.AccountStorage;

import java.util.HashMap;

//I am not considering multi threaded scenario
public class AccountStorageSingleton {
    private static final AccountStorage instance = new AccountStorage(new HashMap<>());

    public static AccountStorage getInstance() {
        return instance;
    }
}
