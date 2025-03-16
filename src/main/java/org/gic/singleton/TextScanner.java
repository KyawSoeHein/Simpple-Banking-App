package org.gic.singleton;

import java.util.Scanner;

public class TextScanner {
    private static final Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }
}
