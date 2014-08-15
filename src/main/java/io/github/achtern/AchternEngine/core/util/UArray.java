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


    @SuppressWarnings("unchecked")
    public static <E> E[] concat(E[] first, E[] second) {
        E[] merged = (E[]) new Object[first.length + second.length];

        System.arraycopy(first, 0, merged, 0, first.length);
        System.arraycopy(second, 0, merged, first.length - 1, second.length);


        return merged;

    }
}
