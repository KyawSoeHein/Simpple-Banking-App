package org.gic.singleton;

import lombok.Getter;

import java.util.Scanner;

public class TextScanner {
    @Getter
    private static final Scanner scanner = new Scanner(System.in);
}
