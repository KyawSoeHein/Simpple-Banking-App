package org.gic.model;

import java.util.HashMap;

public record AccountStorage(HashMap<String, Account> accountHashMap) {
}
