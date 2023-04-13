package org.example.utility;

import java.util.Scanner;

public class UserInput {
    private static Scanner userScanner;
    private static boolean fileMode = false;

    public static Scanner getUserScanner() {
        return userScanner;
    }

    public static boolean fileMode() {
        return fileMode;
    }

    public static void setUserScanner(Scanner userScanner) {
        UserInput.userScanner = userScanner;
    }

    public static void setFileMode() {
        UserInput.fileMode = true;
    }

    public static void setUserMode() {
        UserInput.fileMode = false;
    }
}
