package util;

import java.io.*;

public class Logger {

    public static final int BLIND = 1;
    public static final int NORMAL = 2;
    public static final int DETAILED = 4;

    private static PrintStream out = System.out;

    private static int level = BLIND;

    public static void log(String s, int levelforthis) {
        if (levelforthis <= level)
            out.println("[LOG]" + s);
    }
}
