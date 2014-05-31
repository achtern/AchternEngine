package io.github.achtern.AchternEngine.core.util;

public class UArray {

    public static String join(String[] strings) {
        StringBuilder joined = new StringBuilder();

        for (String s : strings) {
            joined.append(s);
        }

        return joined.toString();
    }
}
