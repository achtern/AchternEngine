package io.github.achtern.AchternEngine.core.util;

import java.util.ArrayList;

/**
 * Utility for Strings
 */
public class UString {

    /**
     * Removes empty Strins from array
     * @param strings An array of strings
     * @return The same array, but with no empty strings
     */
    public static String[] removeEmptyFromArray(String[] strings) {
        ArrayList<String> result = new ArrayList<String>();

        for (String string : strings) {
            if (!string.equals("")) {
                result.add(string);
            }
        }

        String[] res = new String[result.size()];
        result.toArray(res);

        return res;
    }
}
