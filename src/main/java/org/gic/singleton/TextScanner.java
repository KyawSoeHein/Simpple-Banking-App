package org.gic.singleton;

import org.gic.model.AccountStorage;

import java.util.Scanner;

public class TextScanner {
    private static final Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }
}
