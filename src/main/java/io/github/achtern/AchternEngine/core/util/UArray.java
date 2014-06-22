package io.github.achtern.AchternEngine.core.util;

/**
 * Utility for Arrays
 */
public class UArray {

    /**
     * Joins an array of strings into one.
     * @param strings The strings to get joined
     * @return the joined String
     */
    public static String join(String[] strings) {
        StringBuilder joined = new StringBuilder();

        for (String s : strings) {
            joined.append(s);
        }

        return joined.toString();
    }
}
