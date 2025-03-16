package org.gic.singleton;

import java.util.HashMap;

//I am not considering multi threaded scenario
public class AccountStorage {
    private static final org.gic.model.AccountStorage instance = new org.gic.model.AccountStorage(new HashMap<>());

    public static org.gic.model.AccountStorage getInstance() {
        return instance;
    }
}
